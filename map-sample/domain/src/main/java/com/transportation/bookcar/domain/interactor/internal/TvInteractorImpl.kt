package com.transportation.bookcar.domain.interactor.internal

import io.reactivex.Single
import com.transportation.bookcar.common.api.ApiException
import com.transportation.bookcar.common.api.ERROR_CODE_NETWORK
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.data.repo.LocalRepo
import com.transportation.bookcar.domain.R
import com.transportation.bookcar.domain.interactor.TvInteractor
import com.transportation.bookcar.domain.pojo.PageList
import com.transportation.bookcar.domain.pojo.Tv
import com.transportation.bookcar.domain.transform.toPageList
import com.transportation.bookcar.domain.transform.toTv
import javax.inject.Inject

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */
internal class TvInteractorImpl @Inject constructor(
        private val networkUtil: NetworkUtil,
        private val resourceUtil: ResourceUtil,
        private val apiRepo: ApiClient,
        private val localRepo: LocalRepo) : TvInteractor {

    override fun getPopularTvs(page: Int): Single<PageList<Tv>> {
        return if (networkUtil.isConnected()) {
            localRepo.loadUserInfo().flatMap {
                apiRepo.getPopularTV(it.token, page)
            }.map {
                it.toPageList{ tvDto ->
                    tvDto.toTv()
                }
            }
        } else {
            Single.fromCallable { throw ApiException(resourceUtil.getStringResource(R.string.network_error), ERROR_CODE_NETWORK) }
        }
    }

}
