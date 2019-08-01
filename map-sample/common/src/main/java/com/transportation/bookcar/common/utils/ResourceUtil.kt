package com.transportation.bookcar.common.utils

import android.content.Context
import android.support.annotation.StringRes

/**
 * Created by  on 3/14/2018.
 */
class ResourceUtil(val context: Context) {
    fun getStringResource(@StringRes stringRes: Int) = context.getString(stringRes)
}
