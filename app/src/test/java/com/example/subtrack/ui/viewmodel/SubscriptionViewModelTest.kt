package com.example.subtrack.ui.viewmodel

import com.example.subtrack.data.local.dao.SubscriptionDao
import com.example.subtrack.data.local.entity.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.viewmodel.SubscriptionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

// unit tests for SubscriptionViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SubscriptionViewModelTest {
    
    private lateinit var viewModel: SubscriptionViewModel
    private lateinit var repository: SubscriptionRepository
    private lateinit var dao: SubscriptionDao
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // create mock DAO
        dao = mock(SubscriptionDao::class.java)
        
        // setup default mock behavior - return empty list by default
        `when`(dao.getAllSubscriptions()).thenReturn(flowOf(emptyList()))
        
        repository = SubscriptionRepository(dao)
        viewModel = SubscriptionViewModel(repository)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `test subscriptions are loaded from repository`() = runTest {
        // given
        val testSubscriptions = listOf(
            Subscription(
                id = 1,
                name = "Netflix",
                monthlyCost = 15.99,
                renewalDate = System.currentTimeMillis(),
                category = "Entertainment"
            ),
            Subscription(
                id = 2,
                name = "Spotify",
                monthlyCost = 9.99,
                renewalDate = System.currentTimeMillis(),
                category = "Entertainment"
            )
        )
        
        `when`(dao.getAllSubscriptions()).thenReturn(flowOf(testSubscriptions))
        
        // when
        val newViewModel = SubscriptionViewModel(SubscriptionRepository(dao))
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        assertEquals(2, newViewModel.subscriptions.value.size)
        assertEquals("Netflix", newViewModel.subscriptions.value[0].name)
    }
    
    @Test
    fun `test total monthly cost is calculated correctly`() = runTest {
        // given
        val testSubscriptions = listOf(
            Subscription(
                id = 1,
                name = "Netflix",
                monthlyCost = 15.99,
                renewalDate = System.currentTimeMillis(),
                category = "Entertainment"
            ),
            Subscription(
                id = 2,
                name = "Spotify",
                monthlyCost = 10.00,
                renewalDate = System.currentTimeMillis(),
                category = "Entertainment"
            )
        )
        
        `when`(dao.getAllSubscriptions()).thenReturn(flowOf(testSubscriptions))
        
        // when
        val newViewModel = SubscriptionViewModel(SubscriptionRepository(dao))
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        assertEquals(25.99, newViewModel.totalMonthlyCost.value, 0.01)
    }
    
    @Test
    fun `test add subscription calls repository`() = runTest {
        // given
        val subscription = Subscription(
            name = "Netflix",
            monthlyCost = 15.99,
            renewalDate = System.currentTimeMillis(),
            category = "Entertainment"
        )
        
        // when
        viewModel.addSubscription(subscription)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        verify(dao, times(1)).insertSubscription(subscription)
    }
}
