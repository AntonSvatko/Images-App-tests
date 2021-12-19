package com.test.imagesapp.data.model.sizes

import com.google.gson.annotations.SerializedName

data class Sizes(
    @SerializedName("size")
    val list: List<Size>
)