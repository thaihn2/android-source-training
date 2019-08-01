package com.transportation.bookcar.app

import dagger.Component
import dagger.android.AndroidInjector
import com.transportation.bookcar.common.UtilComponent
import com.transportation.bookcar.domain.DomainComponent
import javax.inject.Singleton

/**
 * Created on 3/21/2018.
 */

@Singleton
@Component(
        modules = [AppModule::class, ApplicationContextModule::class],
        dependencies = [DomainComponent::class, UtilComponent::class]
)
interface AppComponent : AndroidInjector<App> {
    override fun inject(app: App)
}
