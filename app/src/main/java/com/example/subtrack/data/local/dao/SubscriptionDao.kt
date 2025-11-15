package com.example.subtrack.data.local.dao

import androidx.room.*
import com.example.subtrack.data.local.entity.Subscription
import kotlinx.coroutines.flow.Flow

// Database operations for subscriptions
@Dao
interface SubscriptionDao {
    
    // add new subscription
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription): Long
    
    // update existing subscription
    @Update
    suspend fun updateSubscription(subscription: Subscription)
    
    // delete subscription
    @Delete
    suspend fun deleteSubscription(subscription: Subscription)
    
    // get all subscriptions
    @Query("SELECT * FROM subscriptions ORDER BY renewalDate ASC")
    fun getAllSubscriptions(): Flow<List<Subscription>>
    
    // get subscription by id
    @Query("SELECT * FROM subscriptions WHERE id = :id")
    fun getSubscriptionById(id: Int): Flow<Subscription?>
    
    // get subscriptions by category
    @Query("SELECT * FROM subscriptions WHERE category = :category ORDER BY renewalDate ASC")
    fun getSubscriptionsByCategory(category: String): Flow<List<Subscription>>
    
    // delete all subscriptions
    @Query("DELETE FROM subscriptions")
    suspend fun deleteAllSubscriptions()
}
