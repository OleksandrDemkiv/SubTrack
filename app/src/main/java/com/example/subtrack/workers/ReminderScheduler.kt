package com.example.subtrack.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

// scheduler for periodic reminder checks
object ReminderScheduler {
    
    private const val WORK_NAME = "renewal_reminder_work"
    
    // schedule daily reminder checks
    fun scheduleDailyReminders(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(true)
            .build()
        
        // run once per day
        val reminderRequest = PeriodicWorkRequestBuilder<RenewalReminderWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.HOURS) // first check after 1 hour
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            reminderRequest
        )
    }
    
    // cancel scheduled reminders
    fun cancelReminders(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
