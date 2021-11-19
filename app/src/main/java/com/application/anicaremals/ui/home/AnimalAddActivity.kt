package com.application.anicaremals.ui.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ActivityAnimalAddBinding
import com.application.anicaremals.databinding.ActivityAnimalAddBinding.inflate
import com.application.anicaremals.databinding.ActivityMainBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class AnimalAddActivity : AppCompatActivity() {


    private lateinit var addActivityBinding: ActivityAnimalAddBinding
    private lateinit var database: DatabaseReference

    private var ImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addActivityBinding = ActivityAnimalAddBinding.inflate(layoutInflater)
        setContentView(addActivityBinding.root)

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA),
                111)

        } else {

            addActivityBinding.ivAnimalImage.setOnClickListener {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 101)
                addActivityBinding.tvaddphoto.visibility = View.GONE
            }
        }
        addActivityBinding.btnsave.setOnClickListener {


            val username = addActivityBinding.etUserName.text.toString()
            val phonenumber = addActivityBinding.etPhoneNumber.text.toString()
            val address = addActivityBinding.etAddress.text.toString()
            val animalcat = addActivityBinding.etAnimalCategory.text.toString()
            val animaldeatils = addActivityBinding.etAnimalDetails.text.toString()
            uploadimage()

            database = FirebaseDatabase.getInstance().getReference("Animacaresxs")
            val save = ResponseModel(username,
                phonenumber,
                address, "Image ",
                animaldeatils,
                animalcat)
            database.child(username).setValue(save).addOnSuccessListener {

            }.addOnFailureListener {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
            }

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadimage() {
        val formatter = SimpleDateFormat("yyy_MM_dd", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")


        ImageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                addActivityBinding.ivAnimalImage.setImageURI(ImageUri)
            }.addOnFailureListener{
                Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            var pic = data?.getParcelableExtra<Bitmap>("data")
            addActivityBinding.ivAnimalImage.setImageBitmap(pic)
            val bitmap = data?.extras?.get("data") as Bitmap
            var bytes : ByteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
            var path = MediaStore.Images.Media.insertImage(this.contentResolver,bitmap,"val",null)
            var uri : Uri = Uri.parse(path)
            ImageUri = uri
            addActivityBinding.ivAnimalImage.setImageURI(ImageUri)
        }

    }

}