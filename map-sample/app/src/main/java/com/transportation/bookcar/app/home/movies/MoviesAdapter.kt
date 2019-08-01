package com.transportation.bookcar.app.home.movies

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.transportation.bookcar.app.IMAGE_PATH
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.R.layout
import com.transportation.bookcar.app.base.adapter.BaseViewHolder
import com.transportation.bookcar.app.base.adapter.createView
import com.transportation.bookcar.app.util.loadUrl
import com.transportation.bookcar.domain.pojo.Movie

/**
 * Created on 5/16/2019.
 */
class MoviesAdapter(val movies: List<Movie>): RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): MovieViewHolder {
        return MovieViewHolder(createView(parent, layout.item_movie))
    }
    
    override fun getItemCount(): Int = movies.size
    
    override fun onBindViewHolder(holder: MovieViewHolder, pos : Int) {
        val movie = movies[pos]
        holder.setTitle(movie.title)
        holder.loadImageUrl("$IMAGE_PATH${movie.posterPath}")
    }
    
}

class MovieViewHolder(itemView: View): BaseViewHolder(itemView){
    @BindView(R.id.iv_poster)
    internal lateinit var vPoster: ImageView
    @BindView(R.id.tv_title)
    internal lateinit var vTitle: TextView
    
    fun loadImageUrl(url: String){
        vPoster.loadUrl(url)
    }
    
    fun setTitle(title: String){
        vTitle.text = title
    }
}
