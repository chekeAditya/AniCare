package com.application.anicaremals.ui.fragments.scanner

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.application.anicaremals.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import kotlinx.android.synthetic.main.activity_animal_details.*

class AnimalDetailsActivity : AppCompatActivity() {

    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        image = intent.getStringExtra("image").toString()

        ivAnimalDetails.setImageURI(image.toUri())

        labelImage()

        fbAddAnimalDetails.setOnClickListener {
            val intent = Intent(baseContext, AddAnimalActivity::class.java)
            intent.putExtra("image", image)
            startActivity(intent)
        }

    }

    private fun labelImage() {
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        labeler.process(getInputImage())
            .addOnSuccessListener {
                for (label in it) {
                    tvLabel.text = label.text
                }
            }
    }

    private fun getInputImage(): InputImage {
            val inputStream = baseContext.contentResolver.openInputStream(image.toUri())
            val bitmap =  BitmapFactory.decodeStream(inputStream)
            return InputImage.fromBitmap(bitmap, 0)
    }

}