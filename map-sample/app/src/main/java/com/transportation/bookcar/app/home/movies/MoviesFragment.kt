package com.transportation.bookcar.app.home.movies

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import butterknife.BindView
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.base.fragment.BaseApiFragment
import com.transportation.bookcar.core.base.Mvp
import com.transportation.bookcar.domain.pojo.Movie

/**
 * Created on 5/16/2019.
 */
class MoviesFragment : BaseApiFragment<MoviesFragmentPresenterContract>(),
        MoviesFragmentViewContract {

    @BindView(R.id.flLoading)
    @Nullable
    lateinit var loading: View

    override fun showLoading() {
        if (this::loading.isInitialized) {
            loading.visibility = View.VISIBLE
        }
    }

    override fun showDialog(mess: String) {
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        if (this::loading.isInitialized) {
            loading.visibility = View.GONE
        }
    }

    override val layoutResId: Int
        get() = R.layout.fragment_movies

//    override fun hideLoading() {
//        super.hideLoading()
//        loading.visibility = View.GONE
//    }
//
//    override fun showLoading() {
//        super.showLoading()
//        loading.visibility = View.VISIBLE
//    }

    @BindView(R.id.rv_datas)
    lateinit var vData: RecyclerView

    override fun showData(movies: List<Movie>) {
        vData.adapter = MoviesAdapter(movies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
