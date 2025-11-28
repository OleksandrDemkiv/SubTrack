# SubTrack - Subscription Management App

A modern Android app for tracking and managing your monthly subscriptions, built with Jetpack Compose and Material Design 3.

## Features

### 📱 Pages
1. **Splash Screen** - Welcome screen with "Get Started" button
2. **Dashboard** - Main screen showing total monthly cost, subscription list, and category breakdown chart
3. **Add Subscription** - Form to create new subscriptions with custom renewal dates
4. **Subscription Details** - View individual subscription information
5. **Edit Subscription** - Modify existing subscription details
6. **Notifications** - Configure reminder settings for upcoming renewals

### ⚡ Functionality
- **Subscription Management** - Add, view, edit, and delete subscriptions
- **Cost Tracking** - Automatically calculates total monthly spending
- **Category Organization** - Group subscriptions by category (Entertainment, Productivity, etc.)
- **Custom Renewal Dates** - Set any renewal date using date picker
- **Visual Analytics** - Pie chart showing spending breakdown by category
- **Reminder Notifications** - Get notified 1, 3, or 7 days before renewal
- **Background Tasks** - WorkManager checks for upcoming renewals daily
- **Shake to Add** - Shake your device to quickly add a new subscription
- **Material Design 3** - Modern UI with custom blue theme (#4A90E2)
- **Data Persistence** - Room Database for subscriptions, DataStore for preferences

### 🧪 Tests

**Unit Tests (10 tests total)**
- `SubscriptionViewModelTest` (3 tests) - Subscription loading, cost calculation, add operation
- `SubscriptionDetailViewModelTest` (3 tests) - Load by ID, update, delete operations  
- `NotificationSettingsViewModelTest` (3 tests) - Initialization, toggle notifications, set reminder days
- `ExampleUnitTest` (1 test) - Default template test

**UI Tests (6 tests)**
- `SplashScreenTest` - Splash screen UI and navigation
- `DashboardScreenTest` - Main screen display and interactions
- `AddSubscriptionScreenTest` - Form input and validation
- `SubscriptionDetailsScreenTest` - Details view
- `EditSubscriptionScreenTest` - Edit functionality
- `NotificationsScreenTest` - Settings UI