package com.itami.calorie_tracker.reports_feature.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen

fun NavGraphBuilder.reportsGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Reports.route,
        startDestination = ReportsGraphScreen.Reports.fullRoute
    ) {
        composable(route = ReportsGraphScreen.Reports.fullRoute) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Reports screen")
            }
        }
    }
}

sealed class ReportsGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Reports : ReportsGraphScreen(route = "reports")

}