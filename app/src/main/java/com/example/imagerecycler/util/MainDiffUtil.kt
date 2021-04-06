package com.example.imagerecycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.imagerecycler.model.PictureGroupModel

class MainDiffUtil(
    private val oldList: List<PictureGroupModel>,
    private val newList: List<PictureGroupModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 주소값 비교 ( === )
        // 고유 값을 비교하는게 좋다.
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // 동등성 비교 ( == )
        // 아이템을 서로 비교하는게 좋다.
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}