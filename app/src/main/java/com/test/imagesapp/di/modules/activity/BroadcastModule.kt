package com.test.imagesapp.di.modules.activity

import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.network.PhotosClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object BroadcastModule {

    @ActivityRetainedScoped
    @Provides
    fun provideLocalManager(
        @ApplicationContext context: Context
    ) = LocalBroadcastManager.getInstance(context)

}