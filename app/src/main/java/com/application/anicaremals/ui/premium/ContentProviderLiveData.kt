package com.application.anicaremals.ui.premium

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import androidx.lifecycle.MutableLiveData

abstract class ContentProviderLiveData<T>(
    private val context: Context,
    private val uri: Uri
) : MutableLiveData<T>() {

    private lateinit var observer: ContentObserver

    override fun onActive() {
        postValue(getContentProvidersValue())
        observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                postValue(getContentProvidersValue())
            }
        }
        context.contentResolver.registerContentObserver(uri,true,observer)
    }

    abstract fun getContentProvidersValue(): T

}