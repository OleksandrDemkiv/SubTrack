package com.example.subtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

// subscription details screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDetailsScreen(
    subscriptionId: Int,
    viewModel: SubscriptionDetailViewModel,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val subscription by viewModel.subscription.collectAsState()
    
    LaunchedEffect(subscriptionId) {
        viewModel.loadSubscription(subscriptionId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Subscription Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        subscription?.let { sub ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // subscription name
                Text(
                    text = sub.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // cost
                Text(
                    text = "€%.2f per month".format(sub.monthlyCost),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // renewal date
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                Text(
                    text = "Renewal Date: ${dateFormat.format(Date(sub.renewalDate))}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // category
                Text(
                    text = "Category: ${sub.category}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // reminder toggle
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Remind me 1 day before renewal",
                            fontSize = 16.sp
                        )
                        Switch(
                            checked = sub.reminderEnabled,
                            onCheckedChange = { enabled ->
                                viewModel.updateSubscription(sub.copy(reminderEnabled = enabled))
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // edit button
                Button(
                    onClick = { onNavigateToEdit(sub.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF4A90E2)
                    ),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF4A90E2)),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = "Edit Subscription",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // delete button
                Button(
                    onClick = {
                        viewModel.deleteSubscription(sub)
                        onNavigateBack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF5350)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = "Delete Subscription",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
