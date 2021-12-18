package com.application.anicaremals.adapters

import com.application.anicaremals.local.responses.ResponseModel

interface ClickListener {

    fun OnClick(responseModel: ResponseModel)
}

interface DeleteOnClick {
    fun onDelete(responseModel: ResponseModel)

    fun onEdit(responseModel: ResponseModel)
}
