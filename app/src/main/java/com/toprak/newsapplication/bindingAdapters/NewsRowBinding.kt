package com.toprak.newsapplication.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.toprak.newsapplication.R
import retrofit2.http.Url

class NewsRowBinding {

    companion object{

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                    .placeholder(R.color.light_gray)
                error(R.drawable.baseline_image_24)
            }
        }
    }
}