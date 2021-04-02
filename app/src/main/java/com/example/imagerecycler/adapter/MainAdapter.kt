package com.example.imagerecycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagerecycler.databinding.ItemPictureBinding
import com.example.imagerecycler.model.PictureModel

class MainAdapter(pictureGroupModel: List<PictureModel>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var pictureList: List<PictureModel> = pictureGroupModel

    inner class MainViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureModel: PictureModel) {
            Glide.with(binding.root)
                .load(pictureModel.path)
                .override(200)
                .centerCrop()
                .into(binding.ivPicture)
            binding.tvId.text = pictureModel.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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