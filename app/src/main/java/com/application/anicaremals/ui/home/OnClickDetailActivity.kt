package com.application.anicaremals.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
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

        val number = intent.getStringExtra("userNumber")

        btndetialcall.setOnClickListener {
            var intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$number"))

            if (ActivityCompat.checkSelfPermission(this@OnClickDetailActivity,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this@OnClickDetailActivity,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    1)
            } else {
                startActivity(intent)
            }
        }
    }
}