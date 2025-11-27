package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.screens.SubscriptionDetailsScreen
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import org.junit.Rule
import org.junit.Test

// ui tests for SubscriptionDetailsScreen - simplified without mocks
class SubscriptionDetailsScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testDetailsScreenHasBackButton() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionDetailViewModel(repository)
            
            SubscriptionDetailsScreen(
                subscriptionId = 1,
                viewModel = viewModel,
                onNavigateToEdit = {},
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }
    
    @Test
    fun testDetailsScreenHasTitle() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionDetailViewModel(repository)
            
            SubscriptionDetailsScreen(
                subscriptionId = 1,
                viewModel = viewModel,
                onNavigateToEdit = {},
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithText("Subscription Details").assertExists()
    }
}
