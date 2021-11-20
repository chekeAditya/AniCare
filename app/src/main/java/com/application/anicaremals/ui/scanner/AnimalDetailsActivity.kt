package com.application.anicaremals.ui.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.application.anicaremals.R
import kotlinx.android.synthetic.main.activity_animal_details.*

class AnimalDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        val image = intent.getStringExtra("image")

        ivAnimalDetails.setImageURI(image?.toUri())

        fbAddAnimalDetails.setOnClickListener {
            val intent = Intent(baseContext,AddAnimalActivity::class.java)
            intent.putExtra("image",image)
            startActivity(intent)
        }

    }
}