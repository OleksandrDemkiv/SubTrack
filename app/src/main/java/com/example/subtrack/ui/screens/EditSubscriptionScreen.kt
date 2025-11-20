package com.example.subtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.subtrack.ui.components.DatePickerDialog
import com.example.subtrack.ui.components.formatDateLong
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import java.util.*

// edit subscription screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSubscriptionScreen(
    subscriptionId: Int,
    viewModel: SubscriptionDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val subscription by viewModel.subscription.collectAsState()
    
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Entertainment") }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }
    
    LaunchedEffect(subscriptionId) {
        viewModel.loadSubscription(subscriptionId)
    }
    
    LaunchedEffect(subscription) {
        subscription?.let { sub ->
            name = sub.name
            cost = sub.monthlyCost.toString()
            category = sub.category
            selectedDate = sub.renewalDate
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Subscription",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // subscription name field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Subscription Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // monthly cost field
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it },
                label = { Text("Monthly Cost") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                prefix = { Text("€") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // next renewal date field - clickable
            OutlinedTextField(
                value = formatDateLong(selectedDate),
                onValueChange = { },
                label = { Text("Next Renewal Date") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select date"
                        )
                    }
                }
            )
            
            // date picker dialog
            if (showDatePicker) {
                DatePickerDialog(
                    onDateSelected = { dateMillis ->
                        selectedDate = dateMillis
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // category dropdown
            var expanded by remember { mutableStateOf(false) }
            val categories = listOf("Entertainment", "Fitness", "Productivity", "Shopping", "Other")
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // save button
            Button(
                onClick = {
                    subscription?.let { sub ->
                        if (name.isNotBlank() && cost.isNotBlank()) {
                            val costValue = cost.toDoubleOrNull() ?: sub.monthlyCost
                            
                            val updatedSubscription = sub.copy(
                                name = name,
                                monthlyCost = costValue,
                                renewalDate = selectedDate,
                                category = category
                            )
                            
                            viewModel.updateSubscription(updatedSubscription)
                            onNavigateBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A90E2)
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = "Save",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
