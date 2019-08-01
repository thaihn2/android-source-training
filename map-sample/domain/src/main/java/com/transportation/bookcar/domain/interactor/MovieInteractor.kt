package com.transportation.bookcar.domain.interactor

import io.reactivex.Single
import com.transportation.bookcar.domain.pojo.PageList
import com.transportation.bookcar.domain.pojo.Movie

/**
 * Created by Dao Thanh HA on 11/20/2018.
 */
interface MovieInteractor {
    fun getPopularMovies(page: Int): Single<PageList<Movie>>
}
