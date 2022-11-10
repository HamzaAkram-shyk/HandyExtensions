package com.handy.extensions.extentions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.handy.extensions.extentions.views.gone
import com.handy.extensions.extentions.views.visible


fun View.animate(isVisible: Boolean, duration: Long = 200, onEnd: (() -> Unit)?) {
    this.animate()
        .alpha(if (isVisible) 1f else 0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                if (isVisible) this@animate.visible() else this@animate.gone()
                onEnd.let {
                    it?.invoke()
                }
            }
        })
}

fun View.animateWithDimension(onEnd: (() -> Unit)?) {
    this.animate().translationY(this.height.toFloat()).setDuration(500)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                onEnd.let {
                    it?.invoke()
                }
            }
        })

}


fun View.fadOutAnimation(
    duration: Long = 300,
    visibility: Int = View.INVISIBLE,
    completion: (() -> Unit)? = null
) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            this.visibility = visibility
            completion?.let {
                it()
            }
        }
}

fun View.fadInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            completion?.let {
                it()
            }
        }
}


fun View.increaseViewSizeWithAnimation(
    increaseValue: Int = 0,
    duration: Long = 300L,
    callback: (() -> Unit)? = null
) {
    val valueAnimator =
        ValueAnimator.ofInt(this.measuredHeight, this.measuredHeight + increaseValue)
    valueAnimator.duration = duration
    valueAnimator.addUpdateListener {
        val animatedValue = valueAnimator.animatedValue as Int
        val layoutParams = this.layoutParams
        layoutParams.height = animatedValue
        this.layoutParams = layoutParams
        callback?.invoke()
    }
    valueAnimator.start()
}
















