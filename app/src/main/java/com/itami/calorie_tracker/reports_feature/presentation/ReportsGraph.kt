package com.itami.calorie_tracker.reports_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.reports_feature.presentation.screens.reports.ReportsScreen
import com.itami.calorie_tracker.reports_feature.presentation.screens.reports.ReportsViewModel

fun NavGraphBuilder.reportsGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Reports.route,
        startDestination = ReportsGraphScreen.Reports.fullRoute
    ) {
        composable(route = ReportsGraphScreen.Reports.fullRoute) {
            val viewModel: ReportsViewModel = hiltViewModel()
            ReportsScreen(
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
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