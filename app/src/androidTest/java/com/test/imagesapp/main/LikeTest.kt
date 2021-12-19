package com.test.imagesapp.main


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.test.imagesapp.R
import com.test.imagesapp.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class LikeTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun likeTest2() {
        onView(isRoot()).perform(waitFor(5000))
        val favoriteImage = onView(
            allOf(
                withId(R.id.item_image),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.popular_recycler_view),
                        10
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        favoriteImage.perform(click())

        val appCompatImageView = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_host),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        pressBack()

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.favorites), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_bottom_navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
        onView(isRoot()).perform(waitFor(5000))
        val favoriteImage2 = onView(
            allOf(
                withId(R.id.item_image),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.favorites_recycler_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        favoriteImage2.perform(click())
//        onView(isRoot()).perform(waitFor(5000))
//        val imageView = onView(
//            allOf(
//                withParent(withParent(withId(R.id.main_host))),
//                isDisplayed()
//            )
//        )
//        imageView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: androidx.test.espresso.UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
