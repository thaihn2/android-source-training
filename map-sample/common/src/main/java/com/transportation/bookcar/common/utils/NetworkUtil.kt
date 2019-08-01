package com.transportation.bookcar.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created on 3/15/2018.
 */
class NetworkUtil(private val context: Context) {
    fun get(): Context = context
    
    @SuppressLint("MissingPermission")
    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isAvailable && connectivityManager.activeNetworkInfo.isConnected
    }
}
