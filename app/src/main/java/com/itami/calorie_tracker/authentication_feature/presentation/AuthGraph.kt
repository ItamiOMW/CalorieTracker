package com.itami.calorie_tracker.authentication_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.ImageLoader
import com.itami.calorie_tracker.authentication_feature.presentation.screens.age.AgeScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.age.AgeViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.gender.GenderScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.gender.GenderViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.goal.GoalScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.goal.GoalViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.height.HeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.height.HeightViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle.LifestyleScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle.LifestyleViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients.RecommendedNutrientsScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients.RecommendedNutrientsViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.weight.WeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.weight.WeightViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome.WelcomeScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome.WelcomeViewModel
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen


fun NavGraphBuilder.authGraph(
    navState: NavigationState,
    imageLoader: ImageLoader,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Auth.route,
        startDestination = AuthGraphScreen.Welcome.fullRoute
    ) {
        composable(route = AuthGraphScreen.Welcome.fullRoute) {
            val viewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(
                onNavigateToDiary = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true,
                    )
                },
                onNavigateToGoal = {
                    navState.navigateToScreen(route = AuthGraphScreen.Goal.fullRoute)
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Goal.fullRoute) {
            val viewModel: GoalViewModel = hiltViewModel()
            GoalScreen(
                onNavigateToGender = {
                    navState.navigateToScreen(route = AuthGraphScreen.Gender.fullRoute)
                },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Gender.fullRoute) {
            val viewModel: GenderViewModel = hiltViewModel()
            GenderScreen(
                onNavigateToLifestyle = {
                    navState.navigateToScreen(route = AuthGraphScreen.Lifestyle.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Lifestyle.fullRoute) {
            val viewModel: LifestyleViewModel = hiltViewModel()
            LifestyleScreen(
                onNavigateToHeight = {
                    navState.navigateToScreen(AuthGraphScreen.Height.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Height.fullRoute) {
            val viewModel: HeightViewModel = hiltViewModel()
            HeightScreen(
                onNavigateToWeight = {
                    navState.navigateToScreen(AuthGraphScreen.Weight.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Weight.fullRoute) {
            val viewModel: WeightViewModel = hiltViewModel()
            WeightScreen(
                onNavigateToAge = {
                    navState.navigateToScreen(route = AuthGraphScreen.Age.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.Age.fullRoute) {
            val viewModel: AgeViewModel = hiltViewModel()
            AgeScreen(
                onNavigateToRecommendedPFC = {
                    navState.navigateToScreen(route = AuthGraphScreen.RecommendedNutrients.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.RecommendedNutrients.fullRoute) {
            val viewModel: RecommendedNutrientsViewModel = hiltViewModel()
            RecommendedNutrientsScreen(
                onNavigateToDiary = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true,
                    )
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
    }
}

sealed class AuthGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Welcome : AuthGraphScreen(route = "welcome")

    data object Goal : AuthGraphScreen(route = "goal")

    data object Gender : AuthGraphScreen(route = "gender")

    data object Lifestyle : AuthGraphScreen(route = "lifestyle")

    data object Height : AuthGraphScreen(route = "height")

    data object Weight : AuthGraphScreen(route = "weight")

    data object Age : AuthGraphScreen(route = "age")

    data object RecommendedNutrients : AuthGraphScreen(route = "recommended_pfc")

}