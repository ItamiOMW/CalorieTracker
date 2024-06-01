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
        launchSingleTop: Boolean = true,
        popUpInclusive: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false,
    ) {
        navHostController.navigate(graph) {
            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
            navHostController.currentDestination?.id?.let {  id ->
                popUpTo(id) {
                    this.inclusive = popUpInclusive
                    this.saveState = saveState
                }
            }
        }
    }

    fun navigateToGraph(
        graph: String,
        popUpToId: Int,
        inclusive: Boolean = false,
        launchSingleTop: Boolean = true,
        saveState: Boolean = true,
        restoreState: Boolean = true,
    ) {
        navHostController.navigate(graph) {
            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
            popUpTo(popUpToId) {
                this.saveState = saveState
                this.inclusive = inclusive
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

    fun navigateToScreen(
        route: String,
        saveState: Boolean = true,
        restoreState: Boolean = true,
        launchSingleTop: Boolean = true,
        popUpToRoute: String,
    ) {
        navHostController.navigate(route) {
            popUpTo(popUpToRoute) {
                this.saveState = saveState
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