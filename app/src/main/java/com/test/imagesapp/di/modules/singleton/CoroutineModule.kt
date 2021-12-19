package com.test.imagesapp.di.modules.singleton

import android.os.Handler
import android.os.Looper
import com.test.imagesapp.di.qualifiers.MainCoroutineScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.android.asCoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Provides
    @Singleton
    @MainCoroutineScope
    fun provideMainScope(): CoroutineScope {
        val handler = Handler(Looper.getMainLooper())
        return CoroutineScope(handler.asCoroutineDispatcher("MainLooperCoroutine"))
    }

}