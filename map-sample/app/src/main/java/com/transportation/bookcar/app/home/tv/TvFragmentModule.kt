package com.transportation.bookcar.app.home.tv

import dagger.Binds
import dagger.Module
import com.transportation.bookcar.app.AppFragmentNavigator
import com.transportation.bookcar.core.FragmentNavigator
import com.transportation.bookcar.core.di.PerFragment
import com.transportation.bookcar.core.view.CoreFragment

/**
 * Created on 5/16/2019.
 */
@Module
abstract class TvFragmentModule {
    @Binds
    @PerFragment
    abstract fun presenter(presenter: TvFragmentPresenter): TvFragmentPresenterContract
    
    @Binds
    @PerFragment
    abstract fun view(fragment: TvFragment): TvFragmentViewContract
    
    @Binds
    @PerFragment
    abstract fun fragment(fragment: TvFragment): CoreFragment<*>
    
    @Binds
    @PerFragment
    abstract fun navigator(navigator: AppFragmentNavigator): FragmentNavigator
}
