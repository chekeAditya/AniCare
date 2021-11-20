package com.application.anicaremals.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.anicaremals.databinding.ActivityMainBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.scanner.ScanAnimalActivity
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class MainActivity : AppCompatActivity(), CLickinter {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var list: ArrayList<ResponseModel>
    private lateinit var database: DatabaseReference
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.buttonToAdd.setOnClickListener {
            val intent: Intent = Intent(this, ScanAnimalActivity::class.java)
            startActivity(intent)
        }

        list = arrayListOf<ResponseModel>()
        activityMainBinding.mainrecyclerview.layoutManager = LinearLayoutManager(this)
        activityMainBinding.horizontalrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        getUserData()

    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance().getReference("posts")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(ResponseModel::class.java)
                        list.add(user!!)

                    }

                    var adaptor = AnimalAdaptor(this@MainActivity, list, this@MainActivity)
                    var adaptor1 = HorizontalAdaptor(this@MainActivity, list)
                    activityMainBinding.mainrecyclerview.adapter = adaptor
                    activityMainBinding.horizontalrecycler.adapter = adaptor1
                    adaptor.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.d("sudrshan", "Error")
            }

        })

    }

    override fun OnClick(responseModel: ResponseModel) {
        i++;
        var intent = Intent(this, OnClickDetailActivity::class.java)
        intent.putExtra("username", responseModel.user_name)
        intent.putExtra("animalbread", responseModel.animal_category)
        intent.putExtra("animaldetials", responseModel.animal_details)
        intent.putExtra("userlocation", responseModel.user_address)
        intent.putExtra("userNumber", responseModel.user_phoneNumber)
        intent.putExtra("image", responseModel.animal_image)
        startActivity(intent)
    }
}