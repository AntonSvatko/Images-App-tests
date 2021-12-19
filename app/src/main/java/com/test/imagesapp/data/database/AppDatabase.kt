package com.test.imagesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.imagesapp.data.database.converters.ListConverter
import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.model.Photo

@TypeConverters(ListConverter::class)
@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoritesDao: FavoritesDao
}