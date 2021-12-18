package com.application.anicaremals.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.application.anicaremals.local.responses.ResponseModel
import com.application.anicaremals.ui.fragments.premium.SmsLiveData

class ApplicationViewModels(application: Application) : AndroidViewModel(application) {

    val sms = SmsLiveData(application.applicationContext)

    companion object{
        var livedata = MutableLiveData<List<ResponseModel>>()
    }

}