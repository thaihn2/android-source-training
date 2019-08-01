package com.transportation.bookcar.common.api

import com.google.gson.annotations.SerializedName

/**
 * Created on 3/14/2018.
 */
open class SimpleApiRespond(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
)
