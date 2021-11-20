package com.application.anicaremals.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    lateinit var activityBaseBinding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)

        setContentView(activityBaseBinding.root)
        val navController = findNavController(R.id.navHostFragment)
        activityBaseBinding.bottomNavigationView.setupWithNavController(navController)

    }
}