package com.application.anicaremals.ui.home

import androidx.lifecycle.MutableLiveData
import com.application.anicaremals.remote.response.ResponseModel

class FirebaseLiveDataList {

    companion object{
        var livedata = MutableLiveData<List<ResponseModel>>()
    }

}