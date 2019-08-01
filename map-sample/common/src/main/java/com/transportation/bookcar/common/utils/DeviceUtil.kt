package com.transportation.bookcar.common.utils

import android.content.Context
import android.provider.Settings

/**
 * Created on 3/19/2018.
 */
class DeviceUtil(val context: Context) {
    /**
     * Get device id by old style and not recommend
     * But it is old app logic, so temporary let it be
     * TODO refactor using token
     */
    fun getDeviceId(): String {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
    }
}
