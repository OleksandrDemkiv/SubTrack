package com.example.subtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.subtrack.data.preferences.PreferencesManager
import com.example.subtrack.data.repository.SubscriptionRepository

// Factory for creating ViewModels with dependencies
class ViewModelFactory(
    private val repository: SubscriptionRepository,
    private val preferencesManager: PreferencesManager
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SubscriptionViewModel::class.java) -> {
                SubscriptionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SubscriptionDetailViewModel::class.java) -> {
                SubscriptionDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotificationSettingsViewModel::class.java) -> {
                NotificationSettingsViewModel(preferencesManager) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
