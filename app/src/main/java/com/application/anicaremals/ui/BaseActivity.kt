package com.application.anicaremals.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.anicaremals.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)


    }
}