package com.application.anicaremals.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.application.anicaremals.ui.premium.SmsLiveData

class ApplicationViewModels(application: Application) : AndroidViewModel(application) {

    val sms = SmsLiveData(application.applicationContext)

}