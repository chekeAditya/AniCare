package com.application.anicaremals.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ActivityBaseBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.home.FirebaseLiveDataList
import com.application.anicaremals.ui.scanner.ScanAnimalActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.animal_item_layout.*
import java.time.LocalDate

class BaseActivity : AppCompatActivity() {

    lateinit var activityBaseBinding: ActivityBaseBinding

    private val database = FirebaseDatabase.getInstance().getReference("posts")
    private var list = mutableListOf<ResponseModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBaseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        setContentView(activityBaseBinding.root)

        getUserData()

        val navController = findNavController(R.id.navHostFragment)
        activityBaseBinding.bottomNavigationView.setupWithNavController(navController)

        activityBaseBinding.fbScanPlant.setOnClickListener {
            startActivity(Intent(this,ScanAnimalActivity::class.java))
        }



    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(ResponseModel::class.java)
                        list.add(user!!)
                        FirebaseLiveDataList.livedata.value = list
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", error.message)
            }
        })
    }

}