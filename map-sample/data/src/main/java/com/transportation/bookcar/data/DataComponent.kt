package com.transportation.bookcar.data

import dagger.Component
import com.transportation.bookcar.data.local.DbComponent
import com.transportation.bookcar.data.remote.NetComponent
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.data.repo.LocalRepo

/**
 * Created on 2/9/2018.
 */
//@Singleton
@Component(dependencies = [NetComponent::class, DbComponent::class])
interface DataComponent {
    fun exposeLocalRepo(): LocalRepo

    fun exposeApiRepo(): ApiClient
}
