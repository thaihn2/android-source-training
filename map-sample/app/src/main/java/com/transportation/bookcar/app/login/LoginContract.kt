package com.transportation.bookcar.app.login

import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView

interface LoginViewContract : AndroidView {
    fun showSuccessMessage()
    fun showFailMessage(message: String?)
    fun showLoading()
    fun hideLoading()
    fun showValidEmailOrPassword()
}

interface LoginPresenterContract : AndroidPresenter<LoginViewContract> {

    fun login(email: String, password: String)
}
