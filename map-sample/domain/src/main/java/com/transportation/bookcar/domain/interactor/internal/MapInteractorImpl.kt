package com.transportation.bookcar.domain.interactor.internal

import com.transportation.bookcar.common.api.ApiException
import com.transportation.bookcar.common.api.ERROR_CODE_NETWORK
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.data.repo.LocalRepo
import com.transportation.bookcar.domain.R
import com.transportation.bookcar.domain.interactor.MapInteractor
import com.transportation.bookcar.domain.pojo.Direction
import com.transportation.bookcar.domain.pojo.PageList
import com.transportation.bookcar.domain.pojo.Route
import com.transportation.bookcar.domain.transform.toDirection
import com.transportation.bookcar.domain.transform.toPageList
import com.transportation.bookcar.domain.transform.toRoute
import io.reactivex.Single
import javax.inject.Inject

internal class MapInteractorImpl @Inject constructor(
        private val networkUtil: NetworkUtil,
        private val resourceUtil: ResourceUtil,
        private val apiRepo: ApiClient,
        private val localRepo: LocalRepo
) : MapInteractor {
    override fun getDirection(origin: String, destination: String, key: String): Single<PageList<Route>> {
        return if (networkUtil.isConnected()) {
            localRepo.loadUserInfo().flatMap {
                apiRepo.getDirection(origin, destination, key, null, null, false, null)
            }.map {
                it.toPageList { routeDto ->
                    routeDto.toRoute()
                }
            }
        } else {
            Single.fromCallable { throw ApiException(resourceUtil.getStringResource(R.string.network_error), ERROR_CODE_NETWORK) }
        }
    }

    override fun getDirectionBetweenPlace(origin: String, destination: String, key: String, alternatives: Boolean): Single<Direction<Route>> {
        return if (networkUtil.isConnected()) {
            localRepo.loadUserInfo().flatMap {
                apiRepo.getDirectionBetweenPlace(origin, destination, key, null, null, alternatives, null)
            }.map {
                it.toDirection { routes ->
                    routes.toRoute()
                }
            }
        } else {
            Single.fromCallable { throw ApiException(resourceUtil.getStringResource(R.string.network_error), ERROR_CODE_NETWORK) }
        }
    }
}
