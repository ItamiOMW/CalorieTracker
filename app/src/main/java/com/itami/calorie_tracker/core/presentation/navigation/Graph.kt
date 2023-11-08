package com.itami.calorie_tracker.core.presentation.navigation

sealed class Graph(val route: String) {

    data object ROOT: Graph("root_graph")

    data object ONBOARDING: Graph("onboarding_graph")

    data object AUTH: Graph("auth_graph")

    data object DIARY: Graph("diary_graph")

    data object RECIPES: Graph("recipes_graph")

    data object REPORTS: Graph("reports_graph")

    data object PROFILE: Graph("profile_graph")

}