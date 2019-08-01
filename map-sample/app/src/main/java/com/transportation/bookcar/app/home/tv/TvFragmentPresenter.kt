package com.transportation.bookcar.app.home.tv

import com.transportation.bookcar.common.extension.dispatchAndSubscribe
import com.transportation.bookcar.core.presenter.CorePresenter
import com.transportation.bookcar.domain.interactor.TvInteractor
import javax.inject.Inject

/**
 * Created on 5/16/2019.
 */
class TvFragmentPresenter @Inject constructor(fragment: TvFragmentViewContract) :
        CorePresenter<TvFragmentViewContract>(fragment),
        TvFragmentPresenterContract {
    
    @Inject
    lateinit var tvInteractor: TvInteractor
    
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
        tvInteractor.getPopularTvs(page).dispatchAndSubscribe {
            doOnSuccess {
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
