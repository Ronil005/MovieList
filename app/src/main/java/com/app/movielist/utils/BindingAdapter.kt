package com.app.movielist.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.movielist.R
import com.squareup.picasso.Picasso

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, urlPath: String?) {
        Picasso.get()
            .load(APIConstant.imageURL + urlPath)
            .fit()
            .placeholder(R.drawable.loading)
            .into(imageView)
    }
}