package com.itami.calorie_tracker.onboarding_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.onboarding_feature.presentation.onboarding.OnboardingScreen
import com.itami.calorie_tracker.onboarding_feature.presentation.onboarding.OnboardingViewModel


fun NavGraphBuilder.onboardingGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Onboarding.route,
        startDestination = OnboardingGraphScreen.Onboarding.fullRoute
    ) {
        composable(
            route = OnboardingGraphScreen.Onboarding.fullRoute
        ) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                onNavigateToAuthGraph = {
                    navState.navigateToGraph(
                        graph = Graph.Auth.route,
                        popUpInclusive = true
                    )
                },
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent
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