package com.transportation.bookcar.app.home

import android.support.v4.content.PermissionChecker
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.transportation.bookcar.app.AppActivityNavigator
import com.transportation.bookcar.app.home.movies.MoviesFragment
import com.transportation.bookcar.app.home.movies.MoviesFragmentModule
import com.transportation.bookcar.app.home.tv.TvFragment
import com.transportation.bookcar.app.home.tv.TvFragmentModule
import com.transportation.bookcar.core.ActivityNavigator
import com.transportation.bookcar.core.di.PerActivity
import com.transportation.bookcar.core.di.PerFragment
import com.transportation.bookcar.core.view.CoreActivity
import dagger.Provides

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */

// chua module cua activity
@Module
abstract class HomeActivityModule {

    @Binds
    @PerActivity
    abstract fun homePresenter(presenter: HomePresenter): HomePresenterContract

    @Binds
    @PerActivity
    abstract fun homeView(view: HomeActivity): HomeViewContract

    @Binds
    @PerActivity
    abstract fun activity(mvpPresenter: HomeActivity): CoreActivity<*>

    @Binds
    @PerActivity
    abstract fun navigator(mvpPresenter: AppActivityNavigator): ActivityNavigator
    
    @PerFragment
    @ContributesAndroidInjector(modules = [MoviesFragmentModule::class])
    abstract fun movieFragment(): MoviesFragment
    
    @PerFragment
    @ContributesAndroidInjector(modules = [TvFragmentModule::class])
    abstract fun tvFragment(): TvFragment
    
}
