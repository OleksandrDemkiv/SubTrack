package com.example.subtrack.navigation

// sealed class for type-safe navigation routes
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object AddSubscription : Screen("add_subscription")
    object SubscriptionDetails : Screen("subscription_details/{subscriptionId}") {
        // create route with subscription id
        fun createRoute(subscriptionId: Int) = "subscription_details/$subscriptionId"
    }
    object EditSubscription : Screen("edit_subscription/{subscriptionId}") {
        // create route with subscription id
        fun createRoute(subscriptionId: Int) = "edit_subscription/$subscriptionId"
    }
    object Notifications : Screen("notifications")
}
