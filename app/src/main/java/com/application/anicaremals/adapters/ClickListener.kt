package com.application.anicaremals.adapters

import com.application.anicaremals.localResponse.ResponseModel

interface ClickListener {

    fun OnClick(responseModel: ResponseModel)
}

interface DeleteOnClick {
    fun onDelete(responseModel: ResponseModel)

    fun onEdit(responseModel: ResponseModel)
}

interface Filter{
    fun onFilter(newList: List<ResponseModel>)
}