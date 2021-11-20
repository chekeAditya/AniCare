package com.application.anicaremals.ui.scanner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.application.anicaremals.R
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.BaseActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_animal.*
import kotlinx.android.synthetic.main.activity_animal_details.*

class AddAnimalActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val firebaseReference = firebaseDatabase
        .getReferenceFromUrl("https://anicaremals-default-rtdb.firebaseio.com/")

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val firebaseStorageReference = firebaseStorage
        .getReferenceFromUrl("gs://anicaremals.appspot.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_animal)

        val image = intent.getStringExtra("image")

        ivAnimalAdd.setImageURI(image?.toUri())

        fbAddAnimalConfirm.setOnClickListener {
            if (etAddAnimalCategory.text.length != 0 && etAddAnimalDetails.text.length != 0 &&
                etAddUserName.text.length != 0 && etAddPhoneNo.text.length != 0 &&
                etAddAddress.text.length != 0
            ) {
                uploadImage(image?.toUri())
//                val fragmentTransaction = supportFragmentManager.beginTransaction()
//                fragmentTransaction.replace(R.id.FragmentContanier12, DummyFragment(), "DummyFragment").commit()

                val intent = Intent(this,BaseActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill the Credentials", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun uploadImage(image: Uri?) {
        val fileRef = firebaseStorageReference.child("posts_images")
            .child(System.currentTimeMillis().toString() + ".jpg")
        fileRef.putFile(image!!).continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
            if (!it.isSuccessful()) {
                Log.d("problem", it.exception.toString());
            }
            return@Continuation fileRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateDatabase(task.result)
            }
        }
    }

    private fun updateDatabase(it: Uri) {
        val user_name = etAddUserName.text.toString()
        val user_phoneNumber = etAddPhoneNo.text.toString()
        val user_address = etAddAddress.text.toString()
        val animal_image = it.toString()
        val animal_details = etAddAnimalDetails.text.toString()
        val animal_category = etAddAnimalCategory.text.toString()

        val responseModel = ResponseModel(user_name,
            user_phoneNumber,
            user_address,
            animal_image,
            animal_details,
            animal_category)

        firebaseReference.child("posts").child(user_name).setValue(responseModel)

    }
}