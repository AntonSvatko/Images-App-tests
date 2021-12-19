package com.test.imagesapp.network

import android.util.Log
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.network.data.ApiConstants
import com.test.imagesapp.network.api.PhotosService
import com.test.imagesapp.network.exceptions.PhotosException
import com.test.imagesapp.network.result.ResultOf
import javax.inject.Inject

class PhotosClient @Inject constructor(
    private val photosService: PhotosService
) {

    suspend fun fetchPhotos(): ResultOf<List<Photo>> {
        val data = photosService.fetchPhotos()
        return if (data.stat == ApiConstants.API_OK)
            ResultOf.Success(data.responseData.photos) else errorResponse(data.stat)
    }

    suspend fun fetchSizes(id: String): ResultOf<List<Size>> {
        val data = photosService.fetchSizes(id)
        return if (data.stat == ApiConstants.API_OK)
            ResultOf.Success(data.sizes.list) else errorResponse(data.stat)
    }

        private fun errorResponse(error: String) = ResultOf.Failure(PhotosException(error))

}