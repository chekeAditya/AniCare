package com.application.anicaremals.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.AnimalItemLayoutBinding
import com.application.anicaremals.databinding.ItemHorizontalscroolviewLayoutBinding
import com.application.anicaremals.remote.response.ResponseModel
import com.bumptech.glide.Glide

class AnimalAdaptor(
    var context: Context,
    var list: List<ResponseModel>,
    var listener: CLickinter,
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
        holder.itemLayoutBinding.ivImageDisplay.setOnClickListener {
            listener.OnClick(animallist)
        }
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

        itemLayoutBinding.tvAnimalCategory.text = responseModel.animal_category.toString()
        itemLayoutBinding.tvAnimalDetials.text = responseModel.animal_details.toString()
        Glide.with(itemLayoutBinding.ivImageDisplay).load(responseModel.animal_image)
            .into(itemLayoutBinding.ivImageDisplay)
    }

}

class HorizontalAdaptor(
    var context: Context,
    var list: List<ResponseModel>,
) : RecyclerView.Adapter<HorizontalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalHolder {
        val itemLayoutBinding: ItemHorizontalscroolviewLayoutBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_horizontalscroolview_layout, parent, false)
        return HorizontalHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: HorizontalHolder, position: Int) {
        val animallist = list[position]
        holder.setData(animallist)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class HorizontalHolder(val itemHorizontalscroolviewLayoutBinding: ItemHorizontalscroolviewLayoutBinding) :
    RecyclerView.ViewHolder(itemHorizontalscroolviewLayoutBinding.root) {


    fun setData(responseModel: ResponseModel) {
        itemHorizontalscroolviewLayoutBinding.tvhzname.text = responseModel.animal_category
        Glide.with(itemHorizontalscroolviewLayoutBinding.cihzview).load(responseModel.animal_image)
            .into(
                itemHorizontalscroolviewLayoutBinding.cihzview
            )
    }
}
