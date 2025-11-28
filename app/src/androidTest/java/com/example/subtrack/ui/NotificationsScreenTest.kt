package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.subtrack.data.preferences.PreferencesManager
import com.example.subtrack.ui.screens.NotificationsScreen
import com.example.subtrack.ui.viewmodel.NotificationSettingsViewModel
import org.junit.Rule
import org.junit.Test

// ui tests for NotificationsScreen - simplified without mocks
class NotificationsScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testNotificationsScreenDisplaysToggle() {
        // when
        composeTestRule.setContent {
            // use a real viewmodel for UI tests
            val context = androidx.compose.ui.platform.LocalContext.current
            val prefsManager = PreferencesManager(context)
            val viewModel = NotificationSettingsViewModel(prefsManager)
            
            NotificationsScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithText("Enable Notifications").assertExists()
    }
    
    @Test
    fun testNotificationsScreenHasTitle() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val prefsManager = PreferencesManager(context)
            val viewModel = NotificationSettingsViewModel(prefsManager)
            
            NotificationsScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithText("Notifications").assertExists()
    }
    
    @Test
    fun testBackButtonExists() {
        // when
        composeTestRule.setContent {
            val context = androidx.compose.ui.platform.LocalContext.current
            val prefsManager = PreferencesManager(context)
            val viewModel = NotificationSettingsViewModel(prefsManager)
            
            NotificationsScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }
        
        // then
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }
}
