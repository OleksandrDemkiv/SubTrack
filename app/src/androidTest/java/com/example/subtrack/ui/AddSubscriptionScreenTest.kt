package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.screens.AddSubscriptionScreen
import com.example.subtrack.ui.viewmodel.SubscriptionViewModel
import org.junit.Rule
import org.junit.Test

// ui tests for AddSubscriptionScreen - simplified without mocks
class AddSubscriptionScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testAddScreenDisplaysAllFields() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            AddSubscriptionScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then - check all input fields exist
        composeTestRule.onNodeWithText("Subscription Name", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Monthly Cost", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Next Renewal Date", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Category", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testAddScreenHasSaveButton() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            AddSubscriptionScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then
        composeTestRule.onNodeWithText("Add Subscription", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testAddScreenHasBackButton() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            AddSubscriptionScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then - check for back navigation icon
        composeTestRule.onNodeWithContentDescription("Back", useUnmergedTree = true).assertExists()
    }
    
    @Test
    fun testNameFieldAcceptsInput() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val database = SubTrackDatabase.getDatabase(context)
            val repository = SubscriptionRepository(database.subscriptionDao())
            val viewModel = SubscriptionViewModel(repository)
            
            AddSubscriptionScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // wait for compose to render
        composeTestRule.waitForIdle()
        
        // then - find the first text field (subscription name) and type into it
        composeTestRule.onAllNodes(hasSetTextAction(), useUnmergedTree = true)[0]
            .performTextInput("Netflix")
        
        // verify text was entered
        composeTestRule.onNodeWithText("Netflix", useUnmergedTree = true).assertExists()
    }
}
