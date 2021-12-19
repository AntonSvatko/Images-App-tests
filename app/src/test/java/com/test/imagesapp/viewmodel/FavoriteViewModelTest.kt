package com.test.imagesapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.test.imagesapp.MainCoroutinesRule
import com.test.imagesapp.MockUtil
import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.network.PhotosClient
import com.test.imagesapp.ui.fragments.favorites.FavoritesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FavoriteViewModelTest {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var dataRepository: DataRepository

    private val favoritesDao: FavoritesDao = mock()
    private val photosClient: PhotosClient = mock()

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        dataRepository = DataRepository(photosClient, favoritesDao)
        favoritesViewModel = FavoritesViewModel(dataRepository)
    }

    @Test
    fun testViewModel() : Unit = runBlocking {
        val mockData = MockUtil.createList()

        whenever(favoritesDao.getAllPhotosLiveData())
            .thenReturn(MutableLiveData(mockData))

        val observer : Observer<List<Photo>> = mock()

        favoritesViewModel.photosLiveData.observeForever(observer)

        delay(500)

        verify(favoritesDao, atLeastOnce()).getAllPhotosLiveData()
        verify(observer).onChanged(mockData)
    }

}