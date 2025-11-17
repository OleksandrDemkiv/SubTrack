package com.example.subtrack.data.repository

import com.example.subtrack.data.local.dao.SubscriptionDao
import com.example.subtrack.data.local.entity.Subscription
import kotlinx.coroutines.flow.Flow

// Repository for handling subscription data
class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {
    
    // get all subscriptions
    val allSubscriptions: Flow<List<Subscription>> = subscriptionDao.getAllSubscriptions()
    
    // add subscription
    suspend fun insertSubscription(subscription: Subscription): Long {
        return subscriptionDao.insertSubscription(subscription)
    }
    
    // update subscription
    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.updateSubscription(subscription)
    }
    
    // delete subscription
    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.deleteSubscription(subscription)
    }
    
    // get subscription by id
    fun getSubscriptionById(id: Int): Flow<Subscription?> {
        return subscriptionDao.getSubscriptionById(id)
    }
    
    // get subscriptions by category
    fun getSubscriptionsByCategory(category: String): Flow<List<Subscription>> {
        return subscriptionDao.getSubscriptionsByCategory(category)
    }
    
    // delete all
    suspend fun deleteAllSubscriptions() {
        subscriptionDao.deleteAllSubscriptions()
    }
}
