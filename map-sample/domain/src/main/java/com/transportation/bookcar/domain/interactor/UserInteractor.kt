package com.transportation.bookcar.domain.interactor

import io.reactivex.Single
import com.transportation.bookcar.domain.pojo.User

interface UserInteractor {
    fun loadUserInfo(): Single<User?>
    fun saveUserInfo(user: User): Single<User>

    fun registerDeviceToken(authorization: String, deviceToken: String): Single<Boolean>

    fun saveDeviceToken(token: String)
    fun getDeviceToken(): String
}
