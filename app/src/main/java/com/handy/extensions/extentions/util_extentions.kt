package com.handy.extensions.extentions

import android.content.Context
import android.content.res.Resources
import android.view.View

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.px2dp(px: Float): Int {
    return (px / resources.displayMetrics.density + 0.5f).toInt()
}

fun View.dp2px(dp: Float): Int {
    return (dp * resources.displayMetrics.density + 0.5f).toInt()
}

fun Int.dp2px(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}

fun View.px2sp(px: Float): Int {
    return (px / resources.displayMetrics.scaledDensity + 0.5f).toInt()
}

fun View.sp2px(sp: Float): Int {
    return (sp * resources.displayMetrics.scaledDensity + 0.5f).toInt()
}