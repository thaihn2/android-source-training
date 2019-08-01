package com.transportation.bookcar.app.home.movies

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
abstract class MoviesFragmentModule {
    @Binds
    @PerFragment
    abstract fun presenter(presenter:MoviesFragmentPresenter): MoviesFragmentPresenterContract
    
    @Binds
    @PerFragment
    abstract fun view(fragment: MoviesFragment): MoviesFragmentViewContract
    
    @Binds
    @PerFragment
    abstract fun fragment(fragment: MoviesFragment): CoreFragment<*>
    
    @Binds
    @PerFragment
    abstract fun navigator(navigator: AppFragmentNavigator): FragmentNavigator
}
