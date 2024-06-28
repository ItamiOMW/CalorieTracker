package com.itami.calorie_tracker.settings_feature.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.settings_feature.presentation.screens.account.AccountScreen
import com.itami.calorie_tracker.settings_feature.presentation.screens.change_password.ChangePasswordScreen
import com.itami.calorie_tracker.settings_feature.presentation.screens.language.LanguageScreen
import com.itami.calorie_tracker.settings_feature.presentation.screens.settings.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Settings.route,
        startDestination = SettingsGraphScreen.Settings.fullRoute
    ) {
        composable(
            route = SettingsGraphScreen.Settings.fullRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            },
        ) {
            SettingsScreen(
                onNavigateToAccount = {
                    navHostController.navigate(SettingsGraphScreen.Account.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToLanguage = {
                    navHostController.navigate(SettingsGraphScreen.Language.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSubscription = {
                    onShowSnackbar("Not implemented yet")
                },
                onNavigateBack = navHostController::navigateUp
            )
        }
        composable(
            route = SettingsGraphScreen.Account.fullRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            }
        ) {
            AccountScreen(
                onLogoutSuccessful = {
                    navHostController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Diary.route) {
                            inclusive = true
                        }
                    }
                },
                onDeleteAccountSuccessful = {
                    navHostController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Diary.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToChangePassword = {
                    navHostController.navigate(SettingsGraphScreen.ChangePassword.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateBack = navHostController::navigateUp,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = SettingsGraphScreen.ChangePassword.fullRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            }
        ) {
            ChangePasswordScreen(
                onPasswordChanged = navHostController::navigateUp,
                onNavigateBack = navHostController::navigateUp,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = SettingsGraphScreen.Language.fullRoute,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(250)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(250)
                )
            }
        ) {
            LanguageScreen(onNavigateBack = navHostController::navigateUp)
        }
    }
}

sealed class SettingsGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Settings : SettingsGraphScreen(route = "settings")

    data object Account : SettingsGraphScreen(route = "account")

    data object ChangePassword : SettingsGraphScreen(route = "change_password")

    data object Language : SettingsGraphScreen(route = "language")

}