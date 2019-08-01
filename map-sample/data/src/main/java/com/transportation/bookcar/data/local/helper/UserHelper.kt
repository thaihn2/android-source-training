package com.transportation.bookcar.data.local.helper

import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.queryFirst
import com.vicpin.krealmextensions.save
import io.reactivex.Single
import com.transportation.bookcar.data.fakeUser
import com.transportation.bookcar.data.local.entity.UserEntity

/**
 * Created on 2/2/2018.
 */
internal class UserHelper {
    fun saveUserInfo(user: UserEntity): Single<UserEntity> {
        return Single.fromCallable {
            deleteUserInfo()
            user.save()
            user
        }
    }

    fun loadUserInfo(): Single<UserEntity> {
        return Single.fromCallable {
            UserEntity().queryFirst() ?: fakeUser //TODO remove fakeUser and throw NullPointerException("No user information available")
        }
    }

    private fun deleteUserInfo() {
        UserEntity().deleteAll()
    }
}
