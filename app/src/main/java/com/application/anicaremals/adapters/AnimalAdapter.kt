package com.application.anicaremals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.AnimalItemLayoutBinding
import com.application.anicaremals.local.responses.ResponseModel
import com.bumptech.glide.Glide

class AnimalAdaptor(
    var list: List<ResponseModel>,
    var listener: ClickListener,
) : RecyclerView.Adapter<AnimalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder {
        val itemLayoutBinding: AnimalItemLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.animal_item_layout, parent, false
            )
        return AnimalHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: AnimalHolder, position: Int) {
        val animalList = list[position]
        holder.setData(animalList)
        var i = 0

        holder.itemLayoutBinding.addcardview.setOnClickListener {
            listener.OnClick(animalList)
            i++;
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class AnimalHolder(
    var itemLayoutBinding: AnimalItemLayoutBinding
) : RecyclerView.ViewHolder(itemLayoutBinding.root) {

    fun setData(responseModel: ResponseModel) {

        itemLayoutBinding.apply {
            tvAnimalCategory.text = responseModel.animal_category.toString()
            tvAnimalDetials.text = responseModel.animal_details.toString()
            count.text = responseModel.user_address.toString()
            Glide.with(ivImageDisplay).load(responseModel.animal_image)
                .into(ivImageDisplay)
        }
    }

}