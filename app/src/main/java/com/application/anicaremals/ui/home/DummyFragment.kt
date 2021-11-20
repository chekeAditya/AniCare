package com.application.anicaremals.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.anicaremals.R
import com.application.anicaremals.remote.response.ResponseModel
import com.application.anicaremals.ui.scanner.ScanAnimalActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dummy.*

class DummyFragment : Fragment(R.layout.fragment_dummy), CLickinter {
    private lateinit var database: DatabaseReference
    private lateinit var list: ArrayList<ResponseModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = arrayListOf<ResponseModel>()
        buttonToAdd.setOnClickListener {
            val intent: Intent = Intent(requireContext(), ScanAnimalActivity::class.java)
            startActivity(intent)
        }
        getsuerData()
        profileSection.setOnClickListener {
            var intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getsuerData() {
        database = FirebaseDatabase.getInstance().getReference("posts")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(ResponseModel::class.java)
                        list.add(user!!)

                    }

                    var adaptor = AnimalAdaptor( list, this@DummyFragment)
                    var adaptor1 = HorizontalAdaptor( list)
                    mainrecyclerview.adapter = adaptor
                    horizontalrecycler.adapter = adaptor1
                    adaptor.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.d("sudrshan", "Error")
            }

        })
    }

    override fun OnClick(responseModel: ResponseModel) {
        var intent = Intent(requireContext(), OnClickDetailActivity::class.java)
        intent.putExtra("username", responseModel.user_name)
        intent.putExtra("animalbread", responseModel.animal_category)
        intent.putExtra("animaldetials", responseModel.animal_details)
        intent.putExtra("userlocation", responseModel.user_address)
        intent.putExtra("userNumber", responseModel.user_phoneNumber)
        intent.putExtra("image", responseModel.animal_image)
        startActivity(intent)
    }
}