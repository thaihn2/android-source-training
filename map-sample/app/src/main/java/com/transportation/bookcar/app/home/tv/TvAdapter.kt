package com.transportation.bookcar.app.home.tv

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
import com.transportation.bookcar.domain.pojo.Tv

/**
 * Created on 5/16/2019.
 */
class TvAdapter(val tvs: List<Tv>): RecyclerView.Adapter<TvViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TvViewHolder {
        return TvViewHolder(createView(parent, layout.item_tv))
    }
    
    override fun getItemCount(): Int = tvs.size
    
    override fun onBindViewHolder(holder: TvViewHolder, pos : Int) {
        val tv = tvs[pos]
        holder.setName(tv.name)
        holder.loadImageUrl("$IMAGE_PATH${tv.posterPath}")
    }
    
}

class TvViewHolder(itemView: View): BaseViewHolder(itemView){
    @BindView(R.id.iv_poster)
    internal lateinit var vPoster: ImageView
    @BindView(R.id.tv_name)
    internal lateinit var vName: TextView
    
    fun loadImageUrl(url: String){
        vPoster.loadUrl(url)
    }
    
    fun setName(title: String){
        vName.text = title
    }
}
