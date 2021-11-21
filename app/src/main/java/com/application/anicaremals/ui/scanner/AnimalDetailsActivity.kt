package com.application.anicaremals.ui.scanner

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

        // objectDetectionImage()

        fbAddAnimalDetails.setOnClickListener {
            val intent = Intent(baseContext, AddAnimalActivity::class.java)
            intent.putExtra("image", image)
            startActivity(intent)
        }

    }

    private fun objectDetectionImage() {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .build()

        val objectDetector = ObjectDetection.getClient(options)

        objectDetector.process(getInputImage())
            .addOnSuccessListener { detectedObjects ->
                for (detectedObject in detectedObjects){
                    for (label in detectedObject.labels){
                        tvLabel.text = label.text
                    }
                }
            }
            .addOnFailureListener{
                Log.d("fail",it.toString())
            }

    }

    private fun labelImage() {
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        labeler.process(getInputImage())
            .addOnSuccessListener {
                for (label in it) {
//                    if (label.text == "Cow" || label.text == "Buffalo")
                    tvLabel.text = label.text
//                    else tvLabel.text = "Buffalo"
                }
            }
    }

    private fun getInputImage(): InputImage {
            val inputStream = baseContext.contentResolver.openInputStream(image.toUri())
            val bitmap =  BitmapFactory.decodeStream(inputStream)
            return InputImage.fromBitmap(bitmap, 0)
    }

}