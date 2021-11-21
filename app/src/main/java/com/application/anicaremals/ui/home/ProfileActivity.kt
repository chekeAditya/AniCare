package com.application.anicaremals.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ActivityMainBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.animal_update.view.*

class ProfileActivity : AppCompatActivity(), DeleteOnClick {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var list = mutableListOf<ResponseModel>()
    private lateinit var database: DatabaseReference
    private lateinit var adaptor: ProfileAnimalAdaptor
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101


    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profilerecyclerview.layoutManager = LinearLayoutManager(this)
        getUserData()

        createNodtificationChannel()
    }

    private fun createNodtificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Tittle"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("The List Is as Expected more than 2 and it is ${list.size}")
            .setContentText("The List is More ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
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

                    adaptor = ProfileAnimalAdaptor(this@ProfileActivity, list, this@ProfileActivity)
                    profilerecyclerview.adapter = adaptor
                    adaptor.notifyDataSetChanged()

                    if (list.size >= 2){
                        sendNotification()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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