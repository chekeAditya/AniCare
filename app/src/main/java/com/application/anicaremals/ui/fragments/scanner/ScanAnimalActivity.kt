package com.application.anicaremals.ui.fragments.scanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.application.anicaremals.R
import com.application.anicaremals.util.CONSTANTS.FILENAME_FORMAT
import com.application.anicaremals.util.CONSTANTS.REQUEST_CODE_CAMERA
import kotlinx.android.synthetic.main.activity_scan_animal.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanAnimalActivity : AppCompatActivity() {


    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private lateinit var cameraProvider: ProcessCameraProvider
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_animal)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()

        fbScanAnimal.setOnClickListener {
            takePicture()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.d("Anicare", "startCamera: ${e.message}")
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePicture() {
        gifScanBar.visibility = View.VISIBLE
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    if (intent.getStringExtra("add") != null) {
                        val intent = Intent(baseContext, AddAnimalActivity::class.java)
                        intent.putExtra("image", savedUri.toString())
                        startActivity(intent)
                    } else {
                        val intent = Intent(baseContext, AnimalDetailsActivity::class.java)
                        intent.putExtra("image", savedUri.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            startActivity(intent)
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.d("AniCare", "onError: ${exception.message}")
                }

            })
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) {
            mediaDir
        } else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}