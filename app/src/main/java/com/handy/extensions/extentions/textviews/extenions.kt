package com.handy.extensions.extentions.textviews

import android.graphics.Color
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import com.handy.extensions.extentions.fadInAnimation
import com.handy.extensions.extentions.fadOutAnimation
import com.handy.extensions.extentions.increaseViewSizeWithAnimation

fun TextView.setTextAnimation(
    text: String,
    duration: Long = 300,
    completion: (() -> Unit)? = null
) {
    fadOutAnimation(duration) {
        this.text = text
        fadInAnimation(duration) {
            completion?.let {
                it()
            }
        }
    }
}

fun TextView.getHeightBeforeRender(text: CharSequence = getText()): Int {
    val alignment = when (gravity) {
        Gravity.CENTER -> Layout.Alignment.ALIGN_CENTER
        Gravity.RIGHT -> Layout.Alignment.ALIGN_OPPOSITE
        else -> Layout.Alignment.ALIGN_NORMAL
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        StaticLayout.Builder.obtain(text, 0, text.length, TextPaint(paint), width)
            .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            .setAlignment(alignment)
            .setIncludePad(true).build()
    } else {
        @Suppress("DEPRECATION")
        (StaticLayout(
            text, TextPaint(paint), width, alignment,
            lineSpacingMultiplier, lineSpacingExtra, true
        ))
    }.height
}


fun TextView.makeResizableTextView(
    visibleCharacterCount: Int,
    @ColorRes readMoreColor: Int,
    readMoreVerbiage: String = "read more",
    animationDuration: Long = 300L
) {
    val actualText = this.text
    if (actualText.length <= visibleCharacterCount)
        return
    val span =
        SpannableString(actualText.substring(0, visibleCharacterCount) + "... $readMoreVerbiage")
    val context = this.context
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View) {
            this@makeResizableTextView.text = actualText.toString()
            this@makeResizableTextView.getHeightBeforeRender().also {
                this@makeResizableTextView.increaseViewSizeWithAnimation(
                    increaseValue = it,
                    duration = animationDuration
                )
            }


        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.color = context.resources.getColor(readMoreColor, context.theme)
                } else {
                    ds.color = context.resources.getColor(readMoreColor)
                }
            } catch (e: Exception) {
                ds.color = Color.parseColor("#000000")

            }

        }
    }

    span.setSpan(clickableSpan, span.length - 10, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = span
    this.movementMethod = LinkMovementMethod.getInstance()


}