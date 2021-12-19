package com.test.imagesapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.imagesapp.data.model.Photo
import kotlinx.coroutines.selects.select

@Dao
abstract class FavoritesDao {

    @Insert
    abstract suspend fun insertPhoto(photo: Photo)

    @Delete
    abstract suspend fun deletePhoto(photo: Photo)

    @Update
    abstract suspend fun updatePhoto(photo: Photo)

    @Query("select * from photo_table where id = :id")
    abstract suspend fun getPhoto(id: String): Photo?

    @Transaction
    open suspend fun handlePhoto(photo: Photo): Boolean {
        return if (getPhoto(photo.id) != null) {
            photo.isSaved = false
            deletePhoto(photo)
            false
        } else {
            photo.isSaved = true
            insertPhoto(photo)
            true
        }
    }

    @Query("select * from photo_table")
    abstract fun getAllPhotosLiveData(): LiveData<List<Photo>>

    @Query("select id from photo_table")
    abstract suspend fun getAllPhotosIds(): List<String>

}