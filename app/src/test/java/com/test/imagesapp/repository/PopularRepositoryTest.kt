package com.test.imagesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.*
import com.test.imagesapp.MainCoroutinesRule
import com.test.imagesapp.MockUtil
import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.model.Response
import com.test.imagesapp.data.model.ResponseData
import com.test.imagesapp.data.model.sizes.ResponseSize
import com.test.imagesapp.data.model.sizes.Sizes
import com.test.imagesapp.data.repository.DataRepository
import com.test.imagesapp.network.PhotosClient
import com.test.imagesapp.network.api.PhotosService
import com.test.imagesapp.network.data.ApiConstants
import com.test.imagesapp.network.result.ResultOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.time.Duration

@RunWith(JUnit4::class)
class PopularRepositoryTest {

    private val apiService: PhotosService = mock()
    private val favoritesDao: FavoritesDao = mock()
    private lateinit var photosClient: PhotosClient
    private lateinit var dataRepository: DataRepository

    @get:Rule
    val coroutineRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        photosClient = PhotosClient(apiService)
        dataRepository = DataRepository(photosClient, favoritesDao)
    }

    @Test
    fun testFirstTwoListsOfData(): Unit = runBlocking {
        val mockData = MockUtil.createListWithoutSizes()
        val mockSizes = MockUtil.createSizes()

        val responseData = ResponseData(mockData)
        val response = Response(responseData, ApiConstants.API_OK)
        val responseSizesData = ResponseSize(Sizes(mockSizes), ApiConstants.API_OK)

        val finalData = listOf(mockData.first().copy(sizes = mockSizes))

        whenever(apiService.fetchPhotos()).thenReturn(response)
        whenever(apiService.fetchSizes(mockData[0].id)).thenReturn(responseSizesData)

        dataRepository.fetchPhotos().test(Duration.ZERO) {
            val item = expectItem()
            val resultData = (item as ResultOf.Success).data
            val firstItem = resultData.first()

            dataRepository.fetchSizes(item.data.first().id).test(Duration.ZERO) {
                val sizesItem = expectItem()
                firstItem.sizes = (sizesItem as ResultOf.Success).data
                assertEquals(resultData, finalData)
            }

            assertEquals(resultData, mockData)
            assertEquals(firstItem.title, mockData.first().title)

            assertThat(firstItem.sizes, hasItem(mockSizes.first()))
            assertThat(firstItem.sizes?.size, not(`is`(0)))

            expectComplete()
        }

        verify(apiService, atLeastOnce()).fetchSizes(mockData[0].id)
        verify(apiService, atLeastOnce()).fetchPhotos()
        verifyNoMoreInteractions(apiService)
    }

}