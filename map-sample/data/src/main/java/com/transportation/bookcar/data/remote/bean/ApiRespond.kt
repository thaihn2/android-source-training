package com.transportation.bookcar.data.remote.bean

import com.google.gson.annotations.SerializedName
import com.transportation.bookcar.common.api.SimpleApiRespond

/**
 * Created on 3/8/2018.
 */
class ApiRespond<out T : Any?>(
        code: Int,
        message: String,
        @SerializedName("data")
        val data: T,
        @SerializedName("version")
        var versionInfo: VersionInfo
) : SimpleApiRespond(code, message)
