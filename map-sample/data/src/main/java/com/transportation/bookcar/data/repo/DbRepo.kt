package com.transportation.bookcar.data.repo

import android.content.Context
import io.reactivex.Single
import com.transportation.bookcar.common.utils.PreferenceUtil
import com.transportation.bookcar.data.local.KEY_DEVICE_TOKEN
import com.transportation.bookcar.data.local.entity.UserEntity
import com.transportation.bookcar.data.local.helper.UserHelper

/**
 * Created on 2/2/2018.
 */
internal class DbRepo(context: Context) : LocalRepo, PreferenceUtil(context) {
    
    private val prefs by lazy { defaultPref() }
    
    //region token
    override fun saveDeviceToken(token: String) {
        prefs[KEY_DEVICE_TOKEN] = token
    }

    override fun getDeviceToken(): String = prefs[KEY_DEVICE_TOKEN] ?: ""
    //endregion
    
    //region user
    override fun loadUserInfo(): Single<UserEntity> {
        return UserHelper().loadUserInfo()
    }

    override fun saveUserInfo(user: UserEntity): Single<UserEntity> =
            UserHelper().saveUserInfo(user)
    //endregion
}
