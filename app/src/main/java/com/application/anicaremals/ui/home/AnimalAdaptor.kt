package com.application.anicaremals.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.AnimalItemLayoutBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.bumptech.glide.Glide
import kotlin.coroutines.coroutineContext

class AnimalAdaptor(
    var context: Context,
    var list: List<ResponseModel>,
) : RecyclerView.Adapter<AnimalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder {
        val itemLayoutBinding: AnimalItemLayoutBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.animal_item_layout, parent, false)
        return AnimalHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: AnimalHolder, position: Int) {
        var animallist = list[position]
        holder.setData(animallist)
//        holder.itemLayoutBinding.tvUserName.text = animallist.user_name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class AnimalHolder(
    var itemLayoutBinding: AnimalItemLayoutBinding,
) : RecyclerView.ViewHolder(itemLayoutBinding.root) {

    fun setData(responseModel: ResponseModel) {
        itemLayoutBinding.tvUserName.text = responseModel.user_name.toString()
        itemLayoutBinding.tvAnimalCategory.text = responseModel.animal_category.toString()
        itemLayoutBinding.tvAddress.text = responseModel.user_address.toString()
        itemLayoutBinding.tvPhoneNumber.text = responseModel.user_phoneNumber.toString()
        itemLayoutBinding.tvAnimalDetials.text = responseModel.animal_details.toString()
        Glide.with(itemLayoutBinding.ivImageDisplay).load(responseModel.animal_image)
            .into(itemLayoutBinding.ivImageDisplay)
    }

}
