package com.transportation.bookcar.common.api

import android.os.Bundle

/**
 * Created on 3/26/2018.
 */

const val ERROR_CODE_GENERAL = 4000
const val ERROR_CODE_NETWORK = 4100

data class ApiException(
        val errorMessage: String = "",
        val code: Int = ERROR_CODE_GENERAL,
        val dataBundle: Bundle? = null
) : Exception(errorMessage)
