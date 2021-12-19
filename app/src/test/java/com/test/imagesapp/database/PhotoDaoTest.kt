package com.test.imagesapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.imagesapp.MockUtil
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class PhotoDaoTest : LocalDatabase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testPhotoDaoActions() = runBlocking {
        val photoDao = db.favoritesDao

        val mockData = MockUtil.createPhoto()

        photoDao.insertPhoto(mockData)

        val listData = photoDao.getAllPhotosIds()
        assertThat(listData, hasItem(mockData.id))
    }

}