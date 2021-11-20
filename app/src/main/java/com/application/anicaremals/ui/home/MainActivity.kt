package com.application.anicaremals.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ActivityMainBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.scanner.ScanAnimalActivity
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class MainActivity : AppCompatActivity(), CLickinter {

    private lateinit var activityMainBinding: ActivityMainBinding
    private var list = mutableListOf<ResponseModel>()
    private lateinit var database: DatabaseReference
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.add(R.id.LinearLyaout,DummyFragment(),"Dummy Fragement").commit()

        activityMainBinding.buttonToAdd.setOnClickListener {
            val intent: Intent = Intent(this, ScanAnimalActivity::class.java)
            startActivity(intent)
        }

        activityMainBinding.mainrecyclerview.layoutManager = LinearLayoutManager(this)
        activityMainBinding.horizontalrecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        getUserData()

        activityMainBinding.profileSection.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance().getReference("posts")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(ResponseModel::class.java)
                        list.add(user!!)
                    }

                    var adaptor = AnimalAdaptor(list, this@MainActivity)
                    var adaptor1 = HorizontalAdaptor(list)
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