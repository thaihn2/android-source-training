package com.transportation.bookcar.app.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created on 5/16/2019.
 */
fun ImageView.loadUrl(url: String){
    Glide.with(context).load(url).into(this)
}
