package com.transportation.bookcar.app.home.tv

import com.transportation.bookcar.app.base.api.ApiViewContract
import com.transportation.bookcar.core.base.Mvp
import com.transportation.bookcar.domain.pojo.Tv

/**
 * Created by Nguyen Ngoc QUANG on 3/9/2018.
 */
interface TvFragmentPresenterContract : Mvp.AndroidPresenter<TvFragmentViewContract> {
}

interface TvFragmentViewContract : ApiViewContract {
    fun showData(tvs: List<Tv>)
}
