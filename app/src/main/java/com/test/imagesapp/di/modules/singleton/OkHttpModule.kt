package com.test.imagesapp.di.modules.singleton

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.test.imagesapp.di.qualifiers.ApiOkHttpClient
import com.test.imagesapp.di.qualifiers.PicassoOkHttpClient
import com.test.imagesapp.network.interceptors.ApiQueryInterceptor
import com.test.imagesapp.network.interceptors.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    @Provides
    @Singleton
    @PicassoOkHttpClient
    fun providePicassoClient(cache: Cache): OkHttpClient =
        OkHttpClient.Builder().cache(cache).build()

    @Provides
    @Singleton
    fun provideCache(file: File): Cache = Cache(file, 50 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, "ImagesAppCache")

    @Provides
    @Singleton
    @ApiOkHttpClient
    fun provideApiClient(
        connectivityInterceptor: ConnectivityInterceptor,
        apiQueryInterceptor: ApiQueryInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(apiQueryInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor =
        ConnectivityInterceptor(context)

    @Provides
    @Singleton
    fun provideApiQueryInterceptor(): ApiQueryInterceptor =
        ApiQueryInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }
}