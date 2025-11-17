package com.example.subtrack.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.local.entity.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel for managing subscription list and dashboard
class SubscriptionViewModel(
    private val repository: SubscriptionRepository
) : ViewModel() {
    
    // StateFlow of all subscriptions - UI observes this
    private val _subscriptions = MutableStateFlow<List<Subscription>>(emptyList())
    val subscriptions: StateFlow<List<Subscription>> = _subscriptions.asStateFlow()
    
    // total monthly cost calculated from all subscriptions
    private val _totalMonthlyCost = MutableStateFlow(0.0)
    val totalMonthlyCost: StateFlow<Double> = _totalMonthlyCost.asStateFlow()
    
    init {
        // load subscriptions when ViewModel is created
        loadSubscriptions()
    }
    
    // load all subscriptions from database
    private fun loadSubscriptions() {
        viewModelScope.launch {
            repository.allSubscriptions.collect { subscriptionList ->
                _subscriptions.value = subscriptionList
                // calculate total cost
                _totalMonthlyCost.value = subscriptionList.sumOf { it.monthlyCost }
            }
        }
    }
    
    // add new subscription
    fun addSubscription(subscription: Subscription) {
        viewModelScope.launch {
            repository.insertSubscription(subscription)
        }
    }
    
    // update existing subscription
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
