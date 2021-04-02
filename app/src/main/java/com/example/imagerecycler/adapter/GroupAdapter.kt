package com.example.imagerecycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecycler.adapter.GroupAdapter.GroupViewHolder
import com.example.imagerecycler.databinding.ItemGroupPictureBinding
import com.example.imagerecycler.model.PictureGroupModel

class GroupAdapter(val context: Context) : RecyclerView.Adapter<GroupViewHolder>() {

    // 그룹할 리스트
    private var pictureGroupList = emptyList<PictureGroupModel>()

    inner class GroupViewHolder(private val binding: ItemGroupPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureGroup: PictureGroupModel) {
            binding.tvDate.text = pictureGroup.dateTitle
            binding.pictureGroupRecycler.adapter = MainAdapter(pictureGroup.pictureList)
            binding.pictureGroupRecycler.layoutManager = GridLayoutManager(context, 4)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            ItemGroupPictureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(pictureGroupList[position])
    }

    override fun getItemCount(): Int {
        return pictureGroupList.size
    }

    fun setGroup(newGroupList: List<PictureGroupModel>) {
        pictureGroupList = newGroupList
        notifyDataSetChanged()
    }
}