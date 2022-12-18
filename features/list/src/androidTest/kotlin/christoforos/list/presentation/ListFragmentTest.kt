package christoforos.list.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import christoforos.list.R
import christoforos.test.core.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class ListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun beforeTest() {
        hiltRule.inject()
    }

    //This is a very simple test as a proof of concept for ui testing
    //Pay some time to review the infrastructure implementation of ui testing instead
    @Test
    fun testFavoriteTopBarButtonClick() {
        launchFragmentInHiltContainer<ListFragment>()
        onView(withId(R.id.favorites)).perform(click())
    }

}