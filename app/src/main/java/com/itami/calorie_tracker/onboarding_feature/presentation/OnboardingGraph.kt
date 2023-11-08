package com.itami.calorie_tracker.onboarding_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.ImageLoader
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.onboarding_feature.presentation.onboarding.OnboardingScreen
import com.itami.calorie_tracker.onboarding_feature.presentation.onboarding.OnboardingViewModel


fun NavGraphBuilder.onboardingGraph(
    navState: NavigationState,
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.ONBOARDING.route,
        startDestination = OnboardingGraphScreen.Onboarding.fullRoute
    ) {
        composable(
            route = OnboardingGraphScreen.Onboarding.fullRoute
        ) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(
                onNavigateToAuthGraph = {
                    navState.navigateToGraph(
                        graph = Graph.AUTH.route,
                        popUpInclusive = true
                    )
                },
                imageLoader = imageLoader,
                isDarkTheme = isDarkTheme,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent
            )
        }

    }
}

private sealed class OnboardingGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Onboarding : OnboardingGraphScreen(route = "onboarding")

}