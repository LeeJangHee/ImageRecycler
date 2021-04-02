package com.example.imagerecycler.util

import android.util.Log
import com.example.imagerecycler.model.PictureGroupModel
import com.example.imagerecycler.model.PictureModel

class PictureUtil {

    private val TAG = "janghee"

    fun setDataTitle(pictureList: ArrayList<PictureModel>): List<PictureGroupModel> {
        val pictureGroupList = mutableListOf<PictureGroupModel>()
        // 날짜 데이터 분리
        val dateSet = mutableSetOf<String>()
        for (picture in pictureList) {
            dateSet.add(picture.date)
        }

        // 날짜별로 데이터 그룹 만들기
        for (date in dateSet) {
            val dateOfPicture = mutableListOf<PictureModel>()
            for (picture in pictureList) {
                if (picture.date.contentEquals(date)) {
                    dateOfPicture.add(picture)
                }
            }
            pictureGroupList.add(PictureGroupModel(date, dateOfPicture))
        }

        Log.d(TAG, "날짜 타이틀 사이즈: ${dateSet.size}")
        return pictureGroupList
    }


}