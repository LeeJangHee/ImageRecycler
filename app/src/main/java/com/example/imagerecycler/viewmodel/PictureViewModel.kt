package com.example.imagerecycler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagerecycler.model.PictureGroupModel
import com.example.imagerecycler.model.PictureModel

class PictureViewModel: ViewModel() {
    var _pictureLiveData: MutableLiveData<List<PictureModel>> = MutableLiveData()
    val pictureLiveData: LiveData<List<PictureModel>>
        get() = _pictureLiveData

    var _pictureGroupLiveDate: MutableLiveData<List<PictureGroupModel>> = MutableLiveData()
    val pictureGroupLiveData: LiveData<List<PictureGroupModel>>
        get() = _pictureGroupLiveDate

    fun setPictureData(newDataList: List<PictureModel>) {
        _pictureLiveData.value = newDataList
    }

    fun setPictureGroupData(newGroupItem: List<PictureGroupModel>) {
        _pictureGroupLiveDate.value = newGroupItem
    }

}