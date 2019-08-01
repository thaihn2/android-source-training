package com.transportation.bookcar.app.home.movies

import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.core.presenter.CorePresenter
import com.transportation.bookcar.domain.interactor.MovieInteractor
import javax.inject.Inject

/**
 * Created on 5/16/2019.
 */
class MoviesFragmentPresenter @Inject constructor(fragment: MoviesFragmentViewContract) :
        CorePresenter<MoviesFragmentViewContract>(fragment),
        MoviesFragmentPresenterContract {
    @Inject
    lateinit var movieInteractor: MovieInteractor
    
    private var page: Int = 1
    private var totalPage: Int = 2
    
    override fun onViewCreate() {
        super<CorePresenter>.onViewCreate()
        loadPage()
    }
    
    private fun loadPage() {
        view.showLoading()
        if (page >= totalPage)
            return
        movieInteractor.getPopularMovies(page).dispatchAndSubscribe {
            doOnSuccess {
                view.hideLoading()
                view.showData(it.results)
                page++
                totalPage = it.totalPages
            }
            doHideLoading {
                view.hideLoading()
            }
            doShowError {
                view.hideLoading()
                view.showError(it)
            }
        }
    }
}
