package com.itami.calorie_tracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController,
) {

    fun navigateToGraph(
        graph: String,
        popUpInclusive: Boolean,
    ) {
        navHostController.navigate(graph) {
            popUpTo(navHostController.graph.id) {
                inclusive = popUpInclusive
            }
        }
    }

    fun navigateToScreen(
        route: String,
        saveState: Boolean = true,
        restoreState: Boolean = true,
        launchSingleTop: Boolean = true,
    ) {
        navHostController.navigate(route) {
            navHostController.currentDestination?.id?.let { id ->
                this.popUpTo(id) {
                    this.saveState = saveState
                }
            }
            this.restoreState = restoreState
            this.launchSingleTop = launchSingleTop
        }
    }

    fun navigateBack(
        route: String,
        inclusive: Boolean = false,
    ) {
        if (navHostController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navHostController.popBackStack(
                route = route,
                inclusive = inclusive
            )
        }
    }

    fun navigateBack() {
        if (navHostController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navHostController.popBackStack()
        }
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController(),
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}