package com.itami.calorie_tracker.reports_feature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.reports_feature.presentation.screens.reports.ReportsScreen

fun NavGraphBuilder.reportsGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Reports.route,
        startDestination = ReportsGraphScreen.Reports.fullRoute
    ) {
        composable(route = ReportsGraphScreen.Reports.fullRoute) {
            ReportsScreen(
                onShowSnackbar = onShowSnackbar,
                onNavigateToProfile = {
                    navHostController.navigate(Graph.Profile.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

sealed class ReportsGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Reports : ReportsGraphScreen(route = "reports")

}