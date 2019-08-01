package com.transportation.bookcar.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import com.transportation.bookcar.common.DaggerUtilComponent
import com.transportation.bookcar.common.UtilModule
import com.transportation.bookcar.common.api.ApiConfig
import com.transportation.bookcar.core.AppLogger
import com.transportation.bookcar.data.DaggerDataComponent
import com.transportation.bookcar.data.fakeUser
import com.transportation.bookcar.data.local.DaggerDbComponent
import com.transportation.bookcar.data.local.DbModule
import com.transportation.bookcar.data.remote.DaggerNetComponent
import com.transportation.bookcar.data.remote.NetModule
import com.transportation.bookcar.domain.DaggerDomainComponent
import javax.inject.Inject

/**
 * Created on 9/27/2017.
 */
class App @Inject constructor() : DaggerApplication() {
    val kAppComponent: AppComponent by lazy {
        val utilComponent = DaggerUtilComponent.builder().utilModule(UtilModule(this)).build()
        val netComponent = DaggerNetComponent.builder().netModule(
                NetModule(
                        this,
                        "${BuildConfig.SERVER_API_ADDR}")
        ).build()
        val dbComponent = DaggerDbComponent.builder().dbModule(DbModule(this)).utilComponent(
                utilComponent
        ).build()
        val dataComponent = DaggerDataComponent.builder().netComponent(netComponent).dbComponent(
                dbComponent
        ).build()
        val domainComponent = DaggerDomainComponent.builder()
                .dataComponent(dataComponent)
                .utilComponent(utilComponent)
                .build()
        DaggerAppComponent.builder()
                .applicationContextModule(ApplicationContextModule(this))
                .utilComponent(utilComponent).domainComponent(domainComponent)
                .build()
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = kAppComponent

    override fun onCreate() {
        super.onCreate()
        fakeUser.token = BuildConfig.SERVER_API_KEY //TODO remove fake user logic code
        kAppComponent.inject(this)
        configRealm()
        if (BuildConfig.DEBUG) {
            AppLogger.plant(AppLogger.DebugTree())
        }
        configApiHandler()
    }


    private fun configRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun configApiHandler() {
        ApiConfig.IS_DEBUG = BuildConfig.SHOW_LOG
        ApiConfig.defaultErrorMessage = getString(R.string.all_general_error)
        ApiConfig.onForceLogoutHandler = { error: Throwable ->
            synchronized(this@App) {
                //TODO this way prevent Home screen reopen. It's better if change to use schedule service or pending intent but, user have to wait more. Other solution to use single top but may be its not function if open new task (for ex: open from notification)
                if (!ApiConfig.isForceLogout) {
                    ApiConfig.isForceLogout = true
                    //Do something to logout here. For ex: clear data and go to vLogin activity
                }
            }
        }
        ApiConfig.onForceUpdateHandler = { error: Throwable ->
            synchronized(this@App) {
                //TODO this way prevent Home screen reopen. It's better if change to use schedule service or pending intent but, user have to wait more. Other solution to use single top but may be its not function if open new task (for ex: open from notification)
                if (!ApiConfig.isNewAppVersionAvailable) {
                    ApiConfig.isNewAppVersionAvailable = true
                    //Do something to force update here. For ex: reopen app and recheck version
                }
            }
        }
    }


}

