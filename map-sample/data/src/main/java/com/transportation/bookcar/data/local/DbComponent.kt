package com.transportation.bookcar.data.local

import dagger.Component
import com.transportation.bookcar.common.UtilComponent
import com.transportation.bookcar.data.repo.LocalRepo

/**
 * Created on 2/9/2018.
 */
//@Singleton
@Component(modules = [DbModule::class], dependencies = [UtilComponent::class])
interface DbComponent {
    fun exposeLocalRepo(): LocalRepo
}
