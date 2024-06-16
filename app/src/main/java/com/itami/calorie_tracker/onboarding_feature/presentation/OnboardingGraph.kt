package com.itami.calorie_tracker.onboarding_feature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.onboarding_feature.presentation.onboarding.OnboardingScreen

fun NavGraphBuilder.onboardingGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Onboarding.route,
        startDestination = OnboardingGraphScreen.Onboarding.fullRoute
    ) {
        composable(
            route = OnboardingGraphScreen.Onboarding.fullRoute
        ) {
            OnboardingScreen(
                onShowOnboardingStateSaved = {
                    navHostController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

sealed class OnboardingGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Onboarding : OnboardingGraphScreen(route = "onboarding")

}