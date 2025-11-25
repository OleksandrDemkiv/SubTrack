package com.example.subtrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.subtrack.ui.screens.*
import com.example.subtrack.ui.viewmodel.NotificationSettingsViewModel
import com.example.subtrack.ui.viewmodel.SubscriptionDetailViewModel
import com.example.subtrack.ui.viewmodel.SubscriptionViewModel

// main navigation graph
@Composable
fun Navigation(
    navController: NavHostController,
    subscriptionViewModel: SubscriptionViewModel,
    subscriptionDetailViewModel: SubscriptionDetailViewModel,
    notificationSettingsViewModel: NotificationSettingsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // splash screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // dashboard screen
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                viewModel = subscriptionViewModel,
                onNavigateToAdd = {
                    navController.navigate(Screen.AddSubscription.route)
                },
                onNavigateToDetails = { subscriptionId ->
                    navController.navigate(Screen.SubscriptionDetails.createRoute(subscriptionId))
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                }
            )
        }
        
        // add subscription screen
        composable(Screen.AddSubscription.route) {
            AddSubscriptionScreen(
                viewModel = subscriptionViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // subscription details screen
        composable(
            route = Screen.SubscriptionDetails.route,
            arguments = listOf(
                navArgument("subscriptionId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getInt("subscriptionId") ?: 0
            SubscriptionDetailsScreen(
                subscriptionId = subscriptionId,
                viewModel = subscriptionDetailViewModel,
                onNavigateToEdit = { id ->
                    navController.navigate(Screen.EditSubscription.createRoute(id))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // edit subscription screen
        composable(
            route = Screen.EditSubscription.route,
            arguments = listOf(
                navArgument("subscriptionId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getInt("subscriptionId") ?: 0
            EditSubscriptionScreen(
                subscriptionId = subscriptionId,
                viewModel = subscriptionDetailViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // notifications settings screen
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                viewModel = notificationSettingsViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
