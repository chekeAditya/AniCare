package com.application.anicaremals.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.anicaremals.R
import com.application.anicaremals.remote.response.ResponseModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.animal_update.view.*

class ProfileActivity : AppCompatActivity(), DeleteOnClick {
    private var list = mutableListOf<ResponseModel>()
    private lateinit var database: DatabaseReference
    private lateinit var adaptor: ProfileAnimalAdaptor

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profilerecyclerview.layoutManager = LinearLayoutManager(this)
        getUserData()
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

                    adaptor = ProfileAnimalAdaptor(this@ProfileActivity, list, this@ProfileActivity)
                    profilerecyclerview.adapter = adaptor
                    adaptor.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Error :- ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onDelete(responseModel: ResponseModel) {
        val myDatabase = FirebaseDatabase.getInstance().getReference("posts")
        myDatabase.child(responseModel.user_name.toString()).removeValue()
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        adaptor.notifyDataSetChanged()
    }

    override fun onEdit(responseModel: ResponseModel) {

        val mCtx = this
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Information")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.animal_update, null)
        builder.setView(view)
        val alert = builder.create()
        alert.show()

    }


}