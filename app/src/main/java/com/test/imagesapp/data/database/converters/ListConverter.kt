package com.test.imagesapp.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.di.modules.singleton.NetworkModule
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class ListConverter {

    private val gson: Gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<Size> {
        return gson.fromJson(json, object : TypeToken<List<Size>>() {}.type)
    }

    @TypeConverter
    fun toJson(sizes: List<Size>): String {
        return gson.toJson(sizes)
    }

}