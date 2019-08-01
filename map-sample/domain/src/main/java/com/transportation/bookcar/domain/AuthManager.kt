package com.transportation.bookcar.domain

import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.core.AppLogger
import com.transportation.bookcar.domain.interactor.UserInteractor
import com.transportation.bookcar.domain.pojo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created on 3/19/2018.
 */
@Singleton
class AuthManager @Inject constructor() {
    lateinit var user: User
    @Inject
    protected lateinit var userInteractor: UserInteractor
    
    fun hasUserInfo(): Boolean = synchronized(this) {
        this::user.isInitialized
    }
    
    fun loadUser() {
        if (!hasUserInfo()) {
            userInteractor.loadUserInfo().subscribe(
                    { u ->
                        synchronized(this) {
                            this.user = u.copy()
                        }
                    },
                    { e ->
                        AppLogger.w(e)
                    }
            )
        }
    }
    
    fun getToken(): String = user.token
    
    fun saveUserInfo(user: User) {
        userInteractor.saveUserInfo(user).dispatchAndSubscribe {
            doShowError {
                AppLogger.w(it)
            }
            doOnSuccess { u ->
                synchronized(this) {
                    this@AuthManager.user = u.copy()
                }
            }
        }
    }
}
