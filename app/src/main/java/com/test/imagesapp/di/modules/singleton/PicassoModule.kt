package com.test.imagesapp.di.modules.singleton

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.test.imagesapp.di.qualifiers.PicassoOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PicassoModule {

    @Provides
    @Singleton
    fun providePicasso(
        @ApplicationContext context: Context,
        okHttp3Downloader: OkHttp3Downloader
    ): Picasso {
        return Picasso.Builder(context).downloader(okHttp3Downloader).build()
    }

    @Provides
    @Singleton
    fun provideOkHttp3Downloader(
        @PicassoOkHttpClient okHttpClient: OkHttpClient
    ): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }


}