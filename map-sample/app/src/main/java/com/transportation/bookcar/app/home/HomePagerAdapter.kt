package com.transportation.bookcar.app.home

import android.support.v4.app.Fragment
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.home.movies.MoviesFragment
import com.transportation.bookcar.app.home.tv.TvFragment
import com.transportation.bookcar.core.adapter.BaseFragmentPagerAdapter
import com.transportation.bookcar.core.view.CoreActivity
import com.transportation.bookcar.core.view.CoreFragment

/**
 * Created on 11/20/2018.
 */
class HomePagerAdapter(val activity: CoreActivity<*>) : BaseFragmentPagerAdapter<CoreFragment<*>>(activity) {
    
    override fun getItem(pos: Int): Fragment = when(pos){
        MOVIE_POS -> CoreFragment.newInstance(MoviesFragment::class.java)
        else -> CoreFragment.newInstance(TvFragment::class.java)
    }
    
    override fun getCount(): Int  = PAGE_COUNT
    
    override fun getPageTitle(position: Int): CharSequence? =
         when(position){
            MOVIE_POS -> activity.getString(R.string.tab_movie_title)
            else -> activity.getString(R.string.tab_tv_title)
        }
    
    
    companion object{
        const val MOVIE_POS = 0
        const val PAGE_COUNT = 2
    }
}

