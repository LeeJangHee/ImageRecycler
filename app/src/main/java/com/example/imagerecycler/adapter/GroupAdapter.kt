package com.example.imagerecycler.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecycler.adapter.GroupAdapter.GroupViewHolder
import com.example.imagerecycler.databinding.ItemGroupPictureBinding
import com.example.imagerecycler.model.PictureGroupModel
import com.example.imagerecycler.util.MainDiffUtil

class GroupAdapter(val activity: Activity) : RecyclerView.Adapter<GroupViewHolder>() {

    // 그룹할 리스트
    private var pictureGroupList = emptyList<PictureGroupModel>()

    inner class GroupViewHolder(private val binding: ItemGroupPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureGroup: PictureGroupModel) {

            with(binding) {
                tvDate.text = pictureGroup.dateTitle
                pictureGroupRecycler.adapter = MainAdapter(pictureGroup.pictureList)
                pictureGroupRecycler.layoutManager = GridLayoutManager(activity, 4)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGroupPictureBinding.inflate(inflater, parent, false)
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
        val mainDiffUtil = MainDiffUtil(pictureGroupList, newGroupList)
        val diffUtilResult = DiffUtil.calculateDiff(mainDiffUtil)
        pictureGroupList = newGroupList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}