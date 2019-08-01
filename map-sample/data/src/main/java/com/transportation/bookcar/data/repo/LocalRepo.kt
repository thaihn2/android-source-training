package com.transportation.bookcar.data.repo

import io.reactivex.Single
import com.transportation.bookcar.data.local.entity.UserEntity

/**
 * Created on 2/2/2018.
 */
interface LocalRepo {
    /**
     * save user info from preference
     */
    fun saveDeviceToken(token: String)
    
    /**
     * load user info from preference
     */
    fun getDeviceToken(): String
    
    /**
     * load user info from db
     */
    fun loadUserInfo(): Single<UserEntity>
    
    /**
     * save user info from db
     */
    fun saveUserInfo(user: UserEntity): Single<UserEntity>
}
