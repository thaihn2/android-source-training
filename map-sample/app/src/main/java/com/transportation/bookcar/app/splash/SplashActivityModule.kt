package com.transportation.bookcar.app.splash

import com.transportation.bookcar.app.AppActivityNavigator
import dagger.Binds
import dagger.Module
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.di.PerActivity
import com.transportation.bookcar.core.view.CoreActivity

/**
 * Created on 2/1/2018.
 */
@Module
abstract class SplashActivityModule {
    
    @Binds
    @PerActivity
    abstract fun presenter(presenter: SplashPresenter): SplashPresenterContract
    
    @Binds
    @PerActivity
    abstract fun view(view: SplashActivity): SplashViewContract
    
    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: SplashActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator

}
