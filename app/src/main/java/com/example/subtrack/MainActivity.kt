package com.example.subtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.preferences.PreferencesManager
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.navigation.Navigation
import com.example.subtrack.ui.theme.SubTrackTheme
import com.example.subtrack.ui.viewmodel.NotificationSettingsViewModel
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import com.example.subtrack.ui.viewmodel.SubscriptionViewModel
import com.example.subtrack.ui.viewmodel.ViewModelFactory
import com.example.subtrack.workers.ReminderScheduler

class MainActivity : ComponentActivity() {
    
    // setup database and repository
    private val database by lazy { SubTrackDatabase.getDatabase(this) }
    private val repository by lazy { SubscriptionRepository(database.subscriptionDao()) }
    private val preferencesManager by lazy { PreferencesManager(this) }
    
    // create viewmodels with factory
    private val viewModelFactory by lazy { ViewModelFactory(repository, preferencesManager) }
    
    private val subscriptionViewModel: SubscriptionViewModel by viewModels { viewModelFactory }
    private val subscriptionDetailViewModel: SubscriptionDetailViewModel by viewModels { viewModelFactory }
    private val notificationSettingsViewModel: NotificationSettingsViewModel by viewModels { viewModelFactory }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // schedule background reminder checks
        ReminderScheduler.scheduleDailyReminders(this)
        
        setContent {
            SubTrackTheme {
                val navController = rememberNavController()
                
                Navigation(
                    navController = navController,
                    subscriptionViewModel = subscriptionViewModel,
                    subscriptionDetailViewModel = subscriptionDetailViewModel,
                    notificationSettingsViewModel = notificationSettingsViewModel
                )
            }
        }
    }
}