package com.transportation.bookcar.app.base.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    
    init {
        ButterKnife.bind(this, itemView)
    }
}


fun createView(parent: ViewGroup, @LayoutRes layoutId: Int): View{
    return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
}
