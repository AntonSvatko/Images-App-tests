package com.test.imagesapp

import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.sizes.Size

object MockUtil {

    fun createList() = listOf(createPhoto())

    fun createListWithoutSizes() = listOf(createPhotoWithoutSizes())

    fun createPhoto() = Photo("1", "Brody", "url", createSizes())

    fun createPhotoWithoutSizes() = Photo("1", "Brody", "url" , emptyList())

    fun createSizes() = listOf(Size("Square",320, 240, "https://sdf"))

}