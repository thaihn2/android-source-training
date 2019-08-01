package com.transportation.bookcar.app.register

import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.di.PerActivity
import com.transportation.bookcar.core.view.CoreActivity
import dagger.Binds
import dagger.Module

@Module
abstract class RegisterActivityModule {

    @Binds
    @PerActivity
    abstract fun presenter(presenter: RegisterPresenter): RegisterPresenterContract

    @Binds
    @PerActivity
    abstract fun view(view: RegisterActivity): RegisterViewContract

    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: RegisterActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator
}
