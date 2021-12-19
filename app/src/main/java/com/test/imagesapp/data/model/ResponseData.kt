package com.test.imagesapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("photo")
    val photos: List<Photo>
)