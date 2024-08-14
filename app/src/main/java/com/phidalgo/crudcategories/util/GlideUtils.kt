package com.phidalgo.crudcategories.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtils {
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}
