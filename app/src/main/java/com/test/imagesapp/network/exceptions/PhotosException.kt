package com.test.imagesapp.network.exceptions

class PhotosException(errorCode: String? = null) :
    Exception("Failed to load photos, errorCode = ${errorCode ?: "UNKNOWN"}!")
