package com.transportation.bookcar.data.remote

import dagger.Component
import com.transportation.bookcar.data.remote.rest.ApiClient

/**
 * Created on 3/7/2018.
 */
//@Singleton
@Component(modules = [NetModule::class])
interface NetComponent {
    fun apiRepo(): ApiClient
}
