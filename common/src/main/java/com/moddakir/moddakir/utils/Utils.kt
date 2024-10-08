package com.moddakir.moddakir.utils

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

class Utils {
    fun loadImage(context: Context?, url: String?, imageView: ImageView?) {
        if (imageView != null) try {
            if (context != null) Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView)
        } catch (e: Exception) {
            Log.e("exceptionImage", e.toString())
        }
    }
}