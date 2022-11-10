package com.handy.extensions.extentions.textviews

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.Gravity
import android.widget.TextView
import com.handy.extensions.extentions.fadInAnimation
import com.handy.extensions.extentions.fadOutAnimation

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