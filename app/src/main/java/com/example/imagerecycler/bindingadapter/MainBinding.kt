package com.example.imagerecycler.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object MainBinding {
    @BindingAdapter("loadPictureImage")
    @JvmStatic
    fun loadPictureImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .override(200)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("loadString")
    @JvmStatic
    fun loadId(view: TextView, id: Int) {
        view.text = id.toString()
    }
}