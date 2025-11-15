package com.example.subtrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Subscription data model for Room database
@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // subscription id
    
    val name: String, // subscription name
    
    val monthlyCost: Double, // cost per month
    
    val renewalDate: Long, // next renewal date
    
    val category: String, // subscription category
    
    val reminderEnabled: Boolean = true, // reminder on/off
    
    val createdAt: Long = System.currentTimeMillis() // when created
)
