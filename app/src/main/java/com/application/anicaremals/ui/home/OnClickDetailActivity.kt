package com.application.anicaremals.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.anicaremals.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_on_click_detail.*

class OnClickDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_click_detail)

        tvDetailusername.setText(intent.getStringExtra("username"))
        tvdetailsanmialbread.setText(intent.getStringExtra("animalbread"))
        tvdetialAnimalDescription.setText(intent.getStringExtra("animaldetials"))
        tvUserDettiallocation.setText(intent.getStringExtra("userlocation"))
        Glide.with(tvDetialsImageView).load(intent.getStringExtra("image")).into(tvDetialsImageView)
    }
}