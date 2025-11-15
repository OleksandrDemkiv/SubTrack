package com.example.subtrack.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.subtrack.data.local.dao.SubscriptionDao
import com.example.subtrack.data.local.entity.Subscription

// Room database configuration
@Database(
    entities = [Subscription::class],
    version = 1,
    exportSchema = false
)
abstract class SubTrackDatabase : RoomDatabase() {
    
    abstract fun subscriptionDao(): SubscriptionDao
    
    companion object {
        @Volatile
        private var INSTANCE: SubTrackDatabase? = null
        
        // get database instance
        fun getDatabase(context: Context): SubTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubTrackDatabase::class.java,
                    "subtrack_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
