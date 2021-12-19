package com.test.imagesapp.data.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("photos")
    val responseData: ResponseData,
    val stat: String
)