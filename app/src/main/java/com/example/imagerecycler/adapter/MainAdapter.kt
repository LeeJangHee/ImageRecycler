package com.example.imagerecycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecycler.databinding.ItemPictureBinding
import com.example.imagerecycler.model.PictureModel

class MainAdapter(pictureGroupModel: List<PictureModel>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var pictureList: List<PictureModel> = pictureGroupModel

    class MainViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pictureModel: PictureModel) {

            with(binding) {
                result = pictureModel
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPictureBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(pictureList[position])
    }

    override fun getItemCount(): Int {
        return pictureList.size
    }

    fun setData(newDataList: List<PictureModel>) {
        pictureList = newDataList
        notifyDataSetChanged()
    }

}