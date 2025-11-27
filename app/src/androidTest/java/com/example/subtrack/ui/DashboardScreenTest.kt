package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.screens.DashboardScreen
import com.example.subtrack.ui.viewmodel.SubscriptionViewModel
import org.junit.Rule
import org.junit.Test

// ui tests for DashboardScreen - simplified without mocks
class DashboardScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testDashboardDisplaysTotalCostLabel() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = {},
                onNavigateToDetails = {},
                onNavigateToNotifications = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then
        composeTestRule.onNodeWithText("Total This Month", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testFabButtonExists() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = {},
                onNavigateToDetails = {},
                onNavigateToNotifications = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then - check FAB exists with content description
        composeTestRule.onNodeWithContentDescription("Add Subscription", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testTopBarHasTitle() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = {},
                onNavigateToDetails = {},
                onNavigateToNotifications = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then
        composeTestRule.onNodeWithText("Dashboard", useUnmergedTree = true).assertExists()
    }
}
