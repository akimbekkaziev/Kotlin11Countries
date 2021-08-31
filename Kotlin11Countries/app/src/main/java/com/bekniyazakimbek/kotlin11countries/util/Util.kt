package com.bekniyazakimbek.kotlin11countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bekniyazakimbek.kotlin11countries.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadFromUrl(url: String, placeholderProgressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(placeholderProgressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
    }
}