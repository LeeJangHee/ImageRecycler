package com.example.imagerecycler.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecycler.model.PictureModel

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var listPicture = emptyList<PictureModel>()

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentListPicture = listPicture[position]

    }

    override fun getItemCount(): Int {
        return listPicture.size
    }
}