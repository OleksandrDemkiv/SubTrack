package com.example.subtrack.ui.viewmodel

import com.example.subtrack.data.local.dao.SubscriptionDao
import com.example.subtrack.data.local.entity.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.*

// unit tests for SubscriptionDetailViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SubscriptionDetailViewModelTest {
    
    private lateinit var viewModel: SubscriptionDetailViewModel
    private lateinit var repository: SubscriptionRepository
    private lateinit var dao: SubscriptionDao
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dao = mock(SubscriptionDao::class.java)
        repository = SubscriptionRepository(dao)
        viewModel = SubscriptionDetailViewModel(repository)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `test load subscription by id`() = runTest {
        // given
        val testSubscription = Subscription(
            id = 1,
            name = "Netflix",
            monthlyCost = 15.99,
            renewalDate = System.currentTimeMillis(),
            category = "Entertainment"
        )
        
        `when`(dao.getSubscriptionById(1)).thenReturn(flowOf(testSubscription))
        
        // when
        viewModel.loadSubscription(1)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        assertEquals("Netflix", viewModel.subscription.value?.name)
        assertEquals(15.99, viewModel.subscription.value?.monthlyCost ?: 0.0, 0.01)
    }
    
    @Test
    fun `test update subscription calls repository`() = runTest {
        // given
        val subscription = Subscription(
            id = 1,
            name = "Netflix Updated",
            monthlyCost = 19.99,
            renewalDate = System.currentTimeMillis(),
            category = "Entertainment"
        )
        
        // when
        viewModel.updateSubscription(subscription)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        verify(dao, times(1)).updateSubscription(subscription)
    }
    
    @Test
    fun `test delete subscription calls repository`() = runTest {
        // given
        val subscription = Subscription(
            id = 1,
            name = "Netflix",
            monthlyCost = 15.99,
            renewalDate = System.currentTimeMillis(),
            category = "Entertainment"
        )
        
        // when
        viewModel.deleteSubscription(subscription)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // then
        verify(dao, times(1)).deleteSubscription(subscription)
    }
}
