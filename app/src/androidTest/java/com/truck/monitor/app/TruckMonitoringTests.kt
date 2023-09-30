package com.truck.monitor.app

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.truck.monitor.app.ui.MainActivity
import com.truck.monitor.app.ui.TrucksMonitoringApp
import com.truck.monitor.app.ui.bottomNavigationTestTag
import com.truck.monitor.app.ui.failureContainerTestTag
import com.truck.monitor.app.ui.googleMapTestTag
import com.truck.monitor.app.ui.progressIndicatorTestTag
import com.truck.monitor.app.ui.searchFieldTestTag
import com.truck.monitor.app.ui.sortListingTestTag
import com.truck.monitor.app.ui.topAppbarTestTag
import com.truck.monitor.app.ui.truckInfoCardTestTag
import com.truck.monitor.app.ui.truckInfoHorizontalListTestTag
import com.truck.monitor.app.ui.truckMonitoringListTestTag
import com.truck.monitor.app.ui.truckMonitoringMapTestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class TruckMonitoringTests {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            TrucksMonitoringApp()
        }
    }

    @Test
    fun test_loading_screen_visibility() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = progressIndicatorTestTag)
        )
    }

    @Test
    fun test_top_appbar_visibility() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = topAppbarTestTag)
        )
    }

    @Test
    fun test_bottom_bar_visibility() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = bottomNavigationTestTag)
        )
    }

    @Test
    fun test_switching_tabs() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = bottomNavigationTestTag)
        )
        composeRule.onNodeWithText("Map").performClick()
        composeRule.onNodeWithText("List").performClick()
    }

    @Test
    fun test_truck_monitoring_listview_visibility() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = bottomNavigationTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
    }

    @Test
    fun test_truck_monitoring_mapview_visibility() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = bottomNavigationTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
        composeRule.onNodeWithText("Map").assertIsDisplayed().performClick()
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringMapTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = googleMapTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckInfoHorizontalListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
    }

    @Test
    fun test_sort_truck_info_listing() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = topAppbarTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
        composeRule.onNodeWithTag(sortListingTestTag).assertIsDisplayed().performClick()
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
    }

    @Test
    fun test_search_truck_info_listing() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = topAppbarTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(matcher = hasTestTag(searchFieldTestTag))
        composeRule.onNodeWithTag(searchFieldTestTag).assertIsDisplayed().performTextInput("UAE")

        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = truckMonitoringListTestTag)
                    and hasScrollAction()
                    and hasAnyChild(matcher = hasTestTag(truckInfoCardTestTag))
        )
    }

    @Test
    fun test_search_not_found() {
        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(testTag = topAppbarTestTag)
        )
        composeRule.waitUntilAtLeastOneExists(matcher = hasTestTag(searchFieldTestTag))
        composeRule.onNodeWithTag(searchFieldTestTag).assertIsDisplayed().performTextInput("Lorem Ipsum")

        composeRule.waitUntilAtLeastOneExists(
            matcher = hasTestTag(failureContainerTestTag)
        )

        Espresso.closeSoftKeyboard()

        composeRule.onNodeWithTag(failureContainerTestTag).assertIsDisplayed()
    }

}