package com.example.subtrack.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore for saving app preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {
    
    companion object {
        // keys for storing preferences
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val REMINDER_DAYS = intPreferencesKey("reminder_days")
    }
    
    // get notifications enabled status
    val notificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATIONS_ENABLED] ?: true
        }
    
    // get reminder days before renewal
    val reminderDays: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[REMINDER_DAYS] ?: 7 // default 1 week
        }
    
    // save notifications enabled
    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }
    }
    
    // save reminder days
    suspend fun setReminderDays(days: Int) {
        context.dataStore.edit { preferences ->
            preferences[REMINDER_DAYS] = days
        }
    }
}
