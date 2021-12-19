package com.test.imagesapp

import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.ui.fragments.popular.PopularViewModel
import com.test.imagesapp.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PopularInjectionTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Test
    fun verifyInjection() {
        ActivityScenario.launch(MainActivity::class.java).use {
            it.moveToState(Lifecycle.State.STARTED)

            it.onActivity { activity ->
                val popularViewModel: PopularViewModel by activity.viewModels()

                popularViewModel.photosLiveData.observe(activity) { list ->
                    assertThat(list.first(), isA(Photo::class.java))
                }
            }
        }
    }

}