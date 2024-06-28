package com.itami.calorie_tracker.core.presentation.navigation

sealed class Graph(val route: String) {

    data object Root: Graph("root_graph")

    data object Onboarding: Graph("onboarding_graph")

    data object Auth: Graph("auth_graph")

    data object Diary: Graph("diary_graph")

    data object Recipes: Graph("recipes_graph")

    data object Reports: Graph("reports_graph")

    data object Profile: Graph("profile_graph")

    data object Settings: Graph("settings_graph")

    data object Subscription: Graph("subscription_graph")

}