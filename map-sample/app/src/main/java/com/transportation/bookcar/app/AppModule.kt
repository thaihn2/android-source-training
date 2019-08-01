package com.transportation.bookcar.app

import com.transportation.bookcar.app.home.HomeActivity
import com.transportation.bookcar.app.home.HomeActivityModule
import com.transportation.bookcar.app.login.LoginActivity
import com.transportation.bookcar.app.login.LoginActivityModule
import com.transportation.bookcar.app.register.RegisterActivity
import com.transportation.bookcar.app.register.RegisterActivityModule
import com.transportation.bookcar.app.service.RegisterDeviceTokenServiceModule
import com.transportation.bookcar.app.service.RegistrationDeviceTokenService
import com.transportation.bookcar.app.splash.SplashActivity
import com.transportation.bookcar.app.splash.SplashActivityModule
import com.transportation.bookcar.core.di.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created on 3/21/2018.
 */

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class AppModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector(modules = [RegisterDeviceTokenServiceModule::class])
    abstract fun registrationDeviceTokenService(): RegistrationDeviceTokenService

    @PerActivity
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivityInjector(): HomeActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun loginActivityInjector(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [RegisterActivityModule::class])
    abstract fun registerActivityInjector(): RegisterActivity
}

