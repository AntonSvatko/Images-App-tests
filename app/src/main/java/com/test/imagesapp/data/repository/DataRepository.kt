package com.test.imagesapp.data.repository

import android.util.Log
import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.functions.parallelMap
import com.test.imagesapp.network.PhotosClient
import com.test.imagesapp.network.result.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val photosClient: PhotosClient,
    private val favoritesDao: FavoritesDao
) {

    fun getFavoritesLiveData() = favoritesDao.getAllPhotosLiveData()

    suspend fun getFavoritesIds() = favoritesDao.getAllPhotosIds()

    suspend fun handleFavorite(photo: Photo) = favoritesDao.handlePhoto(photo)

    suspend fun updateFavorite(photo: Photo) = favoritesDao.updatePhoto(photo)

    suspend fun getFavorite(id: String) = favoritesDao.getPhoto(id)

    suspend fun fetchPhotos() = flow {
        val result = photosClient.fetchPhotos()

        emit(result)
    }.flowOn(Dispatchers.IO)

    suspend fun fetchSizes(id: String) = flow {
        val result = photosClient.fetchSizes(id)

        emit(result)
    }.flowOn(Dispatchers.IO)



}