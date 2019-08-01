package com.transportation.bookcar.common.api

/**
 * Created on 4/20/2018.
 */
object ApiConfig {
    var isNewAppVersionAvailable = false
    var isForceLogout = false
    var IS_DEBUG = true
    var defaultErrorMessage = ""
    var FORCE_LOGOUT_CODE = 600
    var FORCE_UPDATE_CODE = 601
    var onForceLogoutHandler: ((Throwable) -> Unit)? = null
    var onForceUpdateHandler: ((Throwable) -> Unit)? = null
}
