package com.example.subtrack.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.subtrack.ui.screens.SplashScreen
import com.example.subtrack.ui.theme.SubTrackTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// instrumentation test for UI components
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun splashScreen_displaysWelcomeText() {
        // given
        composeTestRule.setContent {
            SubTrackTheme {
                SplashScreen(onNavigateToDashboard = {})
            }
        }
        
        // then
        composeTestRule
            .onNodeWithText("Welcome to\nSubTrack")
            .assertIsDisplayed()
    }
    
    @Test
    fun splashScreen_displaysGetStartedButton() {
        // given
        composeTestRule.setContent {
            SubTrackTheme {
                SplashScreen(onNavigateToDashboard = {})
            }
        }
        
        // then
        composeTestRule
            .onNodeWithText("Get Started")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
    
    @Test
    fun splashScreen_buttonClickTriggersNavigation() {
        // given
        var navigationTriggered = false
        
        composeTestRule.setContent {
            SubTrackTheme {
                SplashScreen(onNavigateToDashboard = { navigationTriggered = true })
            }
        }
        
        // when
        composeTestRule
            .onNodeWithText("Get Started")
            .performClick()
        
        // then
        assert(navigationTriggered)
    }
}
