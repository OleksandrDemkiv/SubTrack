package com.example.subtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.local.entity.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel for viewing and editing a single subscription
class SubscriptionDetailViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {
    
    // current subscription being viewed/edited
    private val _subscription = MutableStateFlow<Subscription?>(null)
    val subscription: StateFlow<Subscription?> = _subscription.asStateFlow()
    
    // load subscription by id
    fun loadSubscription(id: Int) {
        viewModelScope.launch {
            repository.getSubscriptionById(id).collect { sub ->
                _subscription.value = sub
            }
        }
    }
    
    // update subscription
    fun updateSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.updateSubscription(subscription)
        }
    }
    
    // delete subscription
    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.deleteSubscription(subscription)
        }
    }
}
