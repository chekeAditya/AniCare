package com.application.anicaremals.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.anicaremals.R
import com.application.anicaremals.databinding.ItemHorizontalscroolviewLayoutBinding
import com.application.anicaremals.localResponse.ResponseModel
import com.bumptech.glide.Glide

class HorizontalAdaptor(
    var list: List<ResponseModel>,
) : RecyclerView.Adapter<HorizontalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalHolder {
        val itemLayoutBinding: ItemHorizontalscroolviewLayoutBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_horizontalscroolview_layout, parent, false
            )
        return HorizontalHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: HorizontalHolder, position: Int) {
        val animalList = list[position]
        holder.setData(animalList)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class HorizontalHolder(private val itemHorizontalScrolViewLayoutBinding: ItemHorizontalscroolviewLayoutBinding) :
    RecyclerView.ViewHolder(itemHorizontalScrolViewLayoutBinding.root) {


    fun setData(responseModel: ResponseModel) {
        Glide.with(itemHorizontalScrolViewLayoutBinding.cihzview).load(responseModel.animal_image)
            .into(
                itemHorizontalScrolViewLayoutBinding.cihzview
            )
    }
}