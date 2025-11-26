package com.example.subtrack.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.subtrack.data.preferences.PreferencesManager
import com.example.subtrack.ui.viewmodel.NotificationSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.google.common.truth.Truth.assertThat

// unit tests for NotificationSettingsViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class NotificationSettingsViewModelTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    private val testDispatcher = StandardTestDispatcher()
    
    private lateinit var viewModel: NotificationSettingsViewModel
    private lateinit var preferencesManager: PreferencesManager
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        preferencesManager = mock(PreferencesManager::class.java)
        
        // setup default mock behavior
        `when`(preferencesManager.notificationsEnabled).thenReturn(flowOf(false))
        `when`(preferencesManager.reminderDays).thenReturn(flowOf(1))
        
        viewModel = NotificationSettingsViewModel(preferencesManager)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `test viewmodel initialization does not crash`() = runTest {
        // given - setup already done
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then - viewmodel should be created successfully
        assertThat(viewModel).isNotNull()
    }
    
    @Test
    fun `test setNotificationsEnabled calls preferences manager`() = runTest {
        // when
        viewModel.setNotificationsEnabled(true)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        verify(preferencesManager, times(1)).setNotificationsEnabled(true)
    }
    
    @Test
    fun `test setReminderDays calls preferences manager`() = runTest {
        // when
        viewModel.setReminderDays(7)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        verify(preferencesManager, times(1)).setReminderDays(7)
    }
}
