package com.example.imagerecycler.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadPictureImage")
fun loadPictureImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .override(400)
        .centerCrop()
        .into(view)
}

@BindingAdapter("loadString")
fun loadId(view: TextView, id: Int) {
    view.text = id.toString()
}
