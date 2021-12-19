package com.test.imagesapp.functions

import com.test.imagesapp.data.model.Photo

fun Photo.validate() = apply{
    if (sizes == null) sizes = emptyList()

}