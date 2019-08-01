package com.transportation.bookcar.common

import android.content.Context
import dagger.Module
import dagger.Provides
import com.transportation.bookcar.common.utils.DeviceUtil
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil

/**
 * Created on 3/21/2018.
 */

@Module
class   UtilModule(val context: Context) {
    @Provides
    fun provideNetworkUtil(): NetworkUtil = NetworkUtil(context)
    
    @Provides
    fun provideResourceUtil(): ResourceUtil = ResourceUtil(context)
    
    @Provides
    fun provideDeviceUtil(): DeviceUtil = DeviceUtil(context)
}
