package com.example.subtrack.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.subtrack.data.local.database.SubTrackDatabase
import com.example.subtrack.data.preferences.PreferencesManager
import com.example.subtrack.notifications.NotificationHelper
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

// worker for checking subscription renewals
class RenewalReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        return try {
            // get database and preferences
            val database = SubTrackDatabase.getDatabase(applicationContext)
            val preferencesManager = PreferencesManager(applicationContext)
            
            // get all subscriptions
            val subscriptions = database.subscriptionDao().getAllSubscriptions().first()
            
            // get notification settings
            val notificationsEnabled = preferencesManager.notificationsEnabled.first()
            val reminderDays = preferencesManager.reminderDays.first()
            
            if (notificationsEnabled) {
                val notificationHelper = NotificationHelper(applicationContext)
                val currentTime = System.currentTimeMillis()
                
                // check each subscription
                subscriptions.forEach { subscription ->
                    if (subscription.reminderEnabled) {
                        val daysUntilRenewal = TimeUnit.MILLISECONDS.toDays(
                            subscription.renewalDate - currentTime
                        ).toInt()
                        
                        // send notification if within reminder window
                        if (daysUntilRenewal in 0..reminderDays) {
                            notificationHelper.showRenewalNotification(
                                subscriptionName = subscription.name,
                                daysUntilRenewal = daysUntilRenewal,
                                notificationId = subscription.id
                            )
                        }
                    }
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
