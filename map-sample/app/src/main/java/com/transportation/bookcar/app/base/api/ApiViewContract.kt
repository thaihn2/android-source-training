package com.transportation.bookcar.app.base.api

import com.transportation.bookcar.core.base.Mvp

/**
 * Created on 3/15/2018.
 */
interface ApiViewContract : Mvp.AndroidView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
}
