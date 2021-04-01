package com.example.imagerecycler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagerecycler.model.PictureModel

class PictureViewModel: ViewModel() {
    var _pictureLiveData: MutableLiveData<List<PictureModel>> = MutableLiveData()
    val pictureLiveData: LiveData<List<PictureModel>>
        get() = _pictureLiveData

    fun setPictureData(newDataList: List<PictureModel>) {
        _pictureLiveData.value = newDataList
    }

    fun getPictureData(): LiveData<List<PictureModel>> {
        return pictureLiveData
    }
}