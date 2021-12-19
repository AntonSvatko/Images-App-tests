package com.test.imagesapp.collections

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CollectionTest {

    @Test
    fun testWindowedMap() {

        val testData = (0..8).toList()

        val windowedData = testData.windowed(2,2,false)

        println(windowedData)
    }

}