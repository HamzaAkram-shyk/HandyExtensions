package com.handy.extensions.extentions.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.handy.extensions.utils.SafeClickListener

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}


fun View.showKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

//
// dimension conversion
//


inline fun View.debounceClick(
    intervalInMillis: Int = 1500,
    crossinline listener: (view: View) -> Unit
) {
    var lastClick = 0L
    setOnClickListener {
        val diff = System.currentTimeMillis() - lastClick
        lastClick = System.currentTimeMillis()
        if (diff > intervalInMillis) {
            listener(it)
        }
    }
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}


fun EditText.placeCursorToEnd() {
    requestFocus()
    this.setSelection(this.text.length)
}

fun View.getMeasurements(parent: View): Pair<Int, Int> {
    measure(
        View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
    )

    return measuredWidth to measuredHeight
}