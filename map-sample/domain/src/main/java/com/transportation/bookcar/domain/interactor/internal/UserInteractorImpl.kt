package com.transportation.bookcar.domain.interactor.internal

import io.reactivex.Single
import com.transportation.bookcar.common.utils.DeviceUtil
import com.transportation.bookcar.common.utils.NetworkUtil
import com.transportation.bookcar.common.utils.ResourceUtil
import com.transportation.bookcar.data.remote.rest.ApiClient
import com.transportation.bookcar.data.repo.LocalRepo
import com.transportation.bookcar.domain.interactor.UserInteractor
import com.transportation.bookcar.domain.pojo.User
import com.transportation.bookcar.domain.transform.toEntity
import com.transportation.bookcar.domain.transform.toUser
import javax.inject.Inject

/**
 * Created on 3/21/2018.
 */
@Suppress("unused")
internal class UserInteractorImpl @Inject constructor(
        private val networkUtil: NetworkUtil,
        private val deviceUtil: DeviceUtil,
        val resourceUtil: ResourceUtil,
        private val apiRepo: ApiClient,
        private val localRepo: LocalRepo
) : UserInteractor {


    override fun loadUserInfo(): Single<User?> {
        return localRepo.loadUserInfo().map { it.toUser() }
    }

    override fun saveUserInfo(user: User): Single<User> = localRepo.saveUserInfo(user.toEntity()).map { it.toUser() }

    //region device token
    override fun registerDeviceToken(
            authorization: String,
            deviceToken: String
    ): Single<Boolean> {
        //TODO Not use for demo, uncomment it if develop real app
//        if (networkUtil.isConnected()) {
//            return apiRepo.registerDeviceToken(
//                    "$KEY_BEARER $authorization",
//                    deviceToken
//            ).map { it ->
//                when {
//                    it.code == HttpURLConnection.HTTP_OK -> true
//                    it.code == ApiConfig.FORCE_UPDATE_CODE -> throw ApiException(
//                            it.message,
//                            it.code,
//                            it.versionInfo.toBundle()
//                    )
//                    else -> throw ApiException(
//                            it.message,
//                            it.code
//                    )
//                }
//            }
//        }
        return Single.just(false)
    }

    override fun saveDeviceToken(token: String) {
        localRepo.saveDeviceToken(token)
    }

    override fun getDeviceToken() = localRepo.getDeviceToken()

}
