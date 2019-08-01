package com.transportation.bookcar.app.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.transportation.bookcar.core.base.Mvp.AndroidPresenter
import com.transportation.bookcar.core.base.Mvp.AndroidView

interface RegisterViewContract : AndroidView {
    fun showLoading()
    fun hideLoading()
    fun showSuccessMessage()
    fun showValidEmailOrPassword()
    fun showValidName()
    fun showFailMessage(message: String?)
    fun showException(authResult: Task<AuthResult>)
}

interface RegisterPresenterContract : AndroidPresenter<RegisterViewContract> {

    fun register(name: String, email: String, password: String, userType: String)
}
