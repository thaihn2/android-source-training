package com.transportation.bookcar.common

import dagger.Component
import com.transportation.bookcar.common.utils.DeviceUtil
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil

/**
 * Created on 2/13/2018.
 */
@Component(modules = [(UtilModule::class)])
interface UtilComponent {
    fun networkUtil(): NetworkUtil
    fun resourceUtil(): ResourceUtil
    fun deviceUtil(): DeviceUtil
}
