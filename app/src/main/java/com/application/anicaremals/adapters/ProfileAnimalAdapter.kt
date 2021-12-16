package com.application.anicaremals.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ProfileAnimalItemLayoutBinding
import com.application.anicaremals.localResponse.ResponseModel
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class ProfileAnimalAdaptor(
    val context: Context,
    private val profileAnimalList: List<ResponseModel>,
    private val listener: DeleteOnClick,
) : RecyclerView.Adapter<ProfileAnimalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAnimalHolder {
        val itemLayoutBinding: ProfileAnimalItemLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.profile_animal_item_layout, parent, false
            )
        return ProfileAnimalHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ProfileAnimalHolder, position: Int) {

        var animalprofile = profileAnimalList[position]
        holder.setData(animalprofile)

        holder.profileAnimalItemLayoutBinding.deleteOption.setOnClickListener {
            listener.onDelete(animalprofile)
        }

        holder.profileAnimalItemLayoutBinding.editoption.setOnClickListener {
            val mCtx = context
            val builder = AlertDialog.Builder(mCtx)
            builder.setTitle("Update Information")
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.animal_update, null)

            holder.profileAnimalItemLayoutBinding.tvAnimalDetials.setText(animalprofile.animal_details)

            builder.setView(view)

            val animalCategory = view.findViewById<EditText>(R.id.AnimalCategory)
            val animalDescription = view.findViewById<EditText>(R.id.Description)
            val animalUserName = view.findViewById<TextView>(R.id.UserName)
            val animalNumber = view.findViewById<EditText>(R.id.Number)
            val contactAddress = view.findViewById<EditText>(R.id.ContactAddress)
            val animalImage = view.findViewById<TextView>(R.id.ImageAddress)

            animalCategory.setText(animalprofile.animal_category)
            animalDescription.setText(animalprofile.animal_details)
            animalUserName.text = animalprofile.user_name
            animalNumber.setText(animalprofile.user_phoneNumber)
            contactAddress.setText(animalprofile.user_address)
            animalImage.text = animalprofile.animal_image

            builder.setPositiveButton("update"
            ) { p0, p1 ->
                val detials =
                    animalCategory.text.toString()
                val category =
                    animalDescription.text.toString()
                val phonenumber =
                    animalNumber.text.toString()

                val address =
                    contactAddress.text.toString()

                val update =
                    ResponseModel(
                        animalUserName.text.toString(),
                        phonenumber,
                        address,
                        animalImage.text.toString(),
                        detials,
                        category
                    )
                val myDatabase = FirebaseDatabase.getInstance().getReference("posts")
                myDatabase.child(update.user_name.toString()).setValue(update)
            }

            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return profileAnimalList.size
    }

}

class ProfileAnimalHolder(
    var profileAnimalItemLayoutBinding: ProfileAnimalItemLayoutBinding,
) : RecyclerView.ViewHolder(profileAnimalItemLayoutBinding.root) {

    fun setData(responseModel: ResponseModel) {
        profileAnimalItemLayoutBinding.apply {
            tvAnimalCategory.text = responseModel.animal_category.toString()
            tvAnimalDetials.text = responseModel.animal_details.toString()
            Glide.with(ivImageDisplay).load(responseModel.animal_image)
                .into(ivImageDisplay)

        }
    }
}