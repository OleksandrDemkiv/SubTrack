package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.screens.EditSubscriptionScreen
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import org.junit.Rule
import org.junit.Test

// ui tests for EditSubscriptionScreen - simplified without mocks
class EditSubscriptionScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testEditScreenHasAllFields() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionDetailViewModel(repository)
            
            EditSubscriptionScreen(
                subscriptionId = 1,
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then - check all editable fields exist
        composeTestRule.onNodeWithText("Subscription Name", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Monthly Cost", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Next Renewal Date", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Category", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testEditScreenHasSaveButton() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionDetailViewModel(repository)
            
            EditSubscriptionScreen(
                subscriptionId = 1,
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then
        composeTestRule.onNodeWithText("Save", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testEditScreenHasBackButton() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionDetailViewModel(repository)
            
            EditSubscriptionScreen(
                subscriptionId = 1,
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }
}
