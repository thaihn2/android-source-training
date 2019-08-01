package com.transportation.bookcar.app.home.tv

import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.base.fragment.BaseApiFragment
import com.transportation.bookcar.domain.pojo.Tv

/**
 * Created on 5/16/2019.
 */
class TvFragment : BaseApiFragment<TvFragmentPresenterContract>(),
                       TvFragmentViewContract {
    override val layoutResId: Int
        get() = R.layout.fragment_tvs

    @BindView(R.id.flLoading)
    @Nullable
    lateinit var loading: View
    override fun showLoading() {
        if (this::loading.isInitialized) {
            loading.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        if (this::loading.isInitialized) {
            loading.visibility = View.GONE
        }
    }
    @BindView(R.id.rv_datas)
    lateinit var vData: RecyclerView
    
    override fun showData(tvs: List<Tv>) {
        vData.adapter = TvAdapter(tvs)
    }
}
