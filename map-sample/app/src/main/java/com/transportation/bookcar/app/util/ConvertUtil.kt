package com.transportation.bookcar.app.util

import android.content.Context
import android.util.TypedValue

object ConvertUtil {
    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
        ).toInt()
    }
}
