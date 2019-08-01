package com.transportation.bookcar.app.home.movies

import com.transportation.bookcar.app.base.api.ApiViewContract
import com.transportation.bookcar.core.base.Mvp
import com.transportation.bookcar.domain.pojo.Movie

/**
 * Created by Nguyen Ngoc QUANG on 3/9/2018.
 */
interface MoviesFragmentPresenterContract : Mvp.AndroidPresenter<MoviesFragmentViewContract> {

}

interface MoviesFragmentViewContract : ApiViewContract {
    fun showData(movies: List<Movie>)
    fun showDialog(mess: String)
}
