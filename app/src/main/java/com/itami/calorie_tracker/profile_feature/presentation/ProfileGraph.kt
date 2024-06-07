package com.itami.calorie_tracker.profile_feature.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.profile_feature.presentation.screens.about_app.AboutAppScreen
import com.itami.calorie_tracker.profile_feature.presentation.screens.profile.ProfileScreen

fun NavGraphBuilder.profileGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Profile.route,
        startDestination = ProfileGraphScreens.Profile.fullRoute
    ) {
        composable(
            route = ProfileGraphScreens.Profile.fullRoute,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                ) + fadeIn(animationSpec = tween(750))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 5
                    }
                ) + fadeOut(animationSpec = tween(750))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 5
                    }
                ) + fadeOut(animationSpec = tween(750))
            }
        ) {
            ProfileScreen(
                onNavigateToMyInfo = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateToCalorieIntake = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateToWaterIntake = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateToSettings = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateToAboutApp = {
                    navState.navigateToScreen(route = ProfileGraphScreens.AboutApp.fullRoute)
                },
                onNavigateToContactUs = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
            )
        }
        composable(route = ProfileGraphScreens.MyInfo.fullRoute) {

        }
        composable(route = ProfileGraphScreens.CalorieIntake.fullRoute) {

        }
        composable(route = ProfileGraphScreens.WaterIntake.fullRoute) {

        }
        composable(route = ProfileGraphScreens.AboutApp.fullRoute) {
            AboutAppScreen(onNavigateBack = navState::navigateBack)
        }
        composable(route = ProfileGraphScreens.ContactUs.fullRoute) {

        }
    }
}

sealed class ProfileGraphScreens(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Profile : ProfileGraphScreens(route = "profile")

    data object MyInfo : ProfileGraphScreens(route = "my_info")

    data object CalorieIntake : ProfileGraphScreens(route = "calorie_intake")

    data object WaterIntake : ProfileGraphScreens(route = "water_intake")

    data object AboutApp : ProfileGraphScreens(route = "about_app")

    data object ContactUs : ProfileGraphScreens(route = "contact_us")

}