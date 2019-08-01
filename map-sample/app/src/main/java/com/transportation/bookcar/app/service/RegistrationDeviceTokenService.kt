package com.transportation.bookcar.app.service

import android.content.Context
import android.os.Bundle
import com.firebase.jobdispatcher.*
import dagger.android.AndroidInjection
import timber.log.Timber
import com.transportation.bookcar.app.KEY_DEVICE_TOKEN
import com.transportation.bookcar.app.REG_DEV_TOKEN_RETRY_SECOND
import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.domain.interactor.UserInteractor
import javax.inject.Inject

/**
 * Created on 4/9/2018.
 */
class RegistrationDeviceTokenService : JobService() {
    
    @Inject
    protected lateinit var userInteractor: UserInteractor
    
    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }
    
    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
    
    override fun onStartJob(job: JobParameters): Boolean {
        Timber.i("$TAG onStartJob")
        var token = job.extras!!.getString(KEY_DEVICE_TOKEN)!!
        Timber.i("$TAG onStartJob $token")
        if (token.isEmpty()) {
            token = userInteractor.getDeviceToken()
        }
        val context = this
        userInteractor.saveDeviceToken(token)
        //        authManager.loadUser()
        //        if (authManager.hasUserInfo()) {
        userInteractor.registerDeviceToken("", token)
                .dispatchAndSubscribe {
                    doOnSuccess { isSucceeded ->
                        Timber.i("registerDeviceToken isSucceeded: $isSucceeded")
                        if (!isSucceeded) {
                            scheduleSendDeviceToken(context, token)
                        }
                    }
                    doShowError {
                        Timber.i("RegistrationDeviceTokenService registerDeviceToken failed")
                        scheduleSendDeviceToken(context, token)
                    }
                }
        //        }
        //        else {
        //            scheduleSendDeviceToken(context, token)
        //        }
        return false
    }
    
    companion object {
        private const val TAG = "RegistrationDeviceToken"
        fun scheduleSendDeviceToken(context: Context, token: String) {
            Timber.i("$TAG token $token")
            val data = Bundle()
            data.putString(KEY_DEVICE_TOKEN, token)
            val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
            val job = dispatcher.newJobBuilder()
                    .setExtras(data)
                    .setService(RegistrationDeviceTokenService::class.java)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setRecurring(false)
                    .setTrigger(Trigger.executionWindow(0, REG_DEV_TOKEN_RETRY_SECOND))
                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                    .setTag(TAG)
                    .setConstraints(
                            // only run on an unmetered network
                            Constraint.ON_UNMETERED_NETWORK
                    )
                    .build()
            dispatcher.mustSchedule(job)
        }
    }
}
