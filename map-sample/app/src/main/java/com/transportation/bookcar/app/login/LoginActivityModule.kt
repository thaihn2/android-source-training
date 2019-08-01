package com.transportation.bookcar.app.login

import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.di.PerActivity
import com.transportation.bookcar.core.view.CoreActivity
import dagger.Binds
import dagger.Module

@Module
abstract class LoginActivityModule {

    @Binds
    @PerActivity
    abstract fun presenter(presenter: LoginPresenter): LoginPresenterContract

    @Binds
    @PerActivity
    abstract fun view(view: LoginActivity): LoginViewContract

    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: LoginActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator
}
