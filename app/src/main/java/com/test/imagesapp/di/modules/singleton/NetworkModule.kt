package com.test.imagesapp.di.modules.singleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.imagesapp.di.qualifiers.ApiOkHttpClient
import com.test.imagesapp.network.data.ApiConstants
import com.test.imagesapp.network.api.PhotosService
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PhotosService {
        return retrofit.create(PhotosService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiOkHttpClient okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstants.API_LINK).client(okHttpClient)
            .addConverterFactory(converterFactory).build()
    }

    @Provides
    @Singleton
    fun provideGsonFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


}