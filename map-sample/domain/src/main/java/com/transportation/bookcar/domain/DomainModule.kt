package com.transportation.bookcar.domain

import com.transportation.bookcar.common.utils.DeviceUtil
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.data.repo.LocalRepo
import com.transportation.bookcar.domain.interactor.*
import com.transportation.bookcar.domain.interactor.internal.*
import dagger.Module
import dagger.Provides

/**
 * Created on 2/7/2018.
 */
@Module()
class DomainModule {

    @Provides
    fun userInteractor(
            networkUtil: NetworkUtil,
            deviceUtil: DeviceUtil,
            resourceUtil: ResourceUtil,
            apiRepo: ApiClient,
            localRepo: LocalRepo
    ): UserInteractor = UserInteractorImpl(
            networkUtil,
            deviceUtil,
            resourceUtil,
            apiRepo,
            localRepo
    )


    @Provides
    fun movieInteractor(
            networkUtil: NetworkUtil,
            resourceUtil: ResourceUtil,
            apiRepo: ApiClient,
            localRepo: LocalRepo
    ): MovieInteractor = MovieInteractorImpl(
            networkUtil,
            resourceUtil,
            apiRepo,
            localRepo
    )

    @Provides
    fun tvInteractor(
            networkUtil: NetworkUtil,
            resourceUtil: ResourceUtil,
            apiRepo: ApiClient,
            localRepo: LocalRepo
    ): TvInteractor = TvInteractorImpl(
            networkUtil,
            resourceUtil,
            apiRepo,
            localRepo
    )

    @Provides
    fun searchLocation(networkUtil: NetworkUtil,
                       resourceUtil: ResourceUtil,
                       apiRepo: ApiClient
    ): SearchLocationInteractor = SearchLocationInteractorImpl(
            networkUtil,
            resourceUtil,
            apiRepo)

    @Provides
    fun mapInterator(
            networkUtil: NetworkUtil,
            resourceUtil: ResourceUtil,
            apiRepo: ApiClient,
            localRepo: LocalRepo
    ): MapInteractor = MapInteractorImpl(
            networkUtil,
            resourceUtil,
            apiRepo,
            localRepo
    )
}
