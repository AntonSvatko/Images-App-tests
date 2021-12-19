package com.test.imagesapp.di.modules.activity

import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.picasso.Picasso
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.di.qualifiers.MainCoroutineScope
import com.test.imagesapp.ui.adapter.ImagesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton


@Module
@InstallIn(FragmentComponent::class)
object ImageAdapterModule {

    @FragmentScoped
    @Provides
    fun provideAdapter(
        picasso: Picasso
    ): ImagesAdapter {
        return ImagesAdapter(picasso)
    }


}