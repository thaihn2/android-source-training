package com.transportation.bookcar.app.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.base.adapter.BaseViewHolder
import com.transportation.bookcar.app.base.adapter.createView
import com.transportation.bookcar.domain.pojo.Candidate

class HomeSearchResultAdapter(private var candidates: List<Candidate>,
                              private var chooseLocationListener: SearchResultHolder.ChooseLocationListener) : RecyclerView.Adapter<SearchResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SearchResultHolder {
        return SearchResultHolder(createView(parent, R.layout.item_search_result))
    }

    override fun getItemCount(): Int {
        return candidates.size
    }

    fun resetCandidateList(candidates: List<Candidate>){
        this.candidates = candidates
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        val candidate = candidates[position]
        holder.loadLocationName(candidate.formatted_address)
        holder.rlHomeRowResultLocation.setOnClickListener {
            chooseLocationListener.onChooseLocation(position)
        }
    }
}

class SearchResultHolder(itemView: View) : BaseViewHolder(itemView) {

    @BindView(R.id.rl_home_row_result_location)
    internal lateinit var rlHomeRowResultLocation: RelativeLayout

    @BindView(R.id.tv_home_location_name)
    internal lateinit var tvHomeLocationName: TextView

    fun loadLocationName(locationName: String) {
        tvHomeLocationName.text = locationName
    }

    interface ChooseLocationListener {
        fun onChooseLocation(position: Int)
    }
}


