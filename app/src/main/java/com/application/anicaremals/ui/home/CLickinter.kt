package com.application.anicaremals.ui.home

import com.application.anicaremals.remote.response.ResponseModel

interface CLickinter {

    fun OnClick(responseModel: ResponseModel)
}

interface DeleteOnClick {
    fun onDelete(responseModel: ResponseModel)

    fun onEdit(responseModel: ResponseModel)
}

interface Filter{
    fun onFilter(list: List<ResponseModel>)
}