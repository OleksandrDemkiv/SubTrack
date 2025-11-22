package com.example.subtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel for notification settings
class NotificationSettingsViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    // notifications enabled status
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()
    
    // reminder days (1, 3, or 7)
    private val _reminderDays = MutableStateFlow(7)
    val reminderDays: StateFlow<Int> = _reminderDays.asStateFlow()
    
    init {
        // load saved settings
        loadSettings()
    }
    
    // load settings from DataStore
    private fun loadSettings() {
        viewModelScope.launch {
            preferencesManager.notificationsEnabled.collect { enabled ->
                _notificationsEnabled.value = enabled
            }
        }
        
        viewModelScope.launch {
            preferencesManager.reminderDays.collect { days ->
                _reminderDays.value = days
            }
        }
    }
    
    // toggle notifications on/off
    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setNotificationsEnabled(enabled)
            _notificationsEnabled.value = enabled
        }
    }
    
    // set reminder days
    fun setReminderDays(days: Int) {
        viewModelScope.launch {
            preferencesManager.setReminderDays(days)
            _reminderDays.value = days
        }
    }
}
