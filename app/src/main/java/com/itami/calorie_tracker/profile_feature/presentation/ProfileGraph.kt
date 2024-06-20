package com.itami.calorie_tracker.profile_feature.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.utils.safeStartActivity
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.profile_feature.presentation.screens.about_app.AboutAppScreen
import com.itami.calorie_tracker.profile_feature.presentation.screens.calorie_intake.CalorieIntakeScreen
import com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us.ContactUsScreen
import com.itami.calorie_tracker.profile_feature.presentation.screens.profile.ProfileScreen
import com.itami.calorie_tracker.profile_feature.presentation.screens.user_info.UserInfoScreen

fun NavGraphBuilder.profileGraph(
    navHostController: NavHostController,
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
                ) + fadeIn(animationSpec = tween(200))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 5
                    }
                ) + fadeOut(animationSpec = tween(200))
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                ) + fadeIn(animationSpec = tween(200))
            },
        ) {
            ProfileScreen(
                onLogoutSuccess = {
                    navHostController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Diary.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToUserInfo = {
                    navHostController.navigate(ProfileGraphScreens.UserInfo.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToCalorieIntake = {
                    navHostController.navigate(ProfileGraphScreens.CalorieIntake.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToAboutApp = {
                    navHostController.navigate(ProfileGraphScreens.AboutApp.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToContactUs = {
                    navHostController.navigate(ProfileGraphScreens.ContactUs.fullRoute) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSettings = {
                    onShowSnackbar("Not implemented yet..")
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
            )
        }
        composable(
            route = ProfileGraphScreens.UserInfo.fullRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            UserInfoScreen(
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onUserInfoSaved = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = ProfileGraphScreens.CalorieIntake.fullRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            CalorieIntakeScreen(
                onNavigateBack = navHostController::navigateUp,
                onCalorieIntakeSaved = navHostController::navigateUp,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = ProfileGraphScreens.AboutApp.fullRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val context = LocalContext.current
            AboutAppScreen(
                onNavigateBack = navHostController::navigateUp,
                onNavigateToGithubSourceCode = {
                    val githubIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://github.com/ItamiOMW/CalorieTracker")
                    }
                    safeStartActivity(
                        context = context,
                        intent = githubIntent,
                        onException = { cause ->
                            onShowSnackbar(cause)
                        }
                    )
                }
            )
        }
        composable(
            route = ProfileGraphScreens.ContactUs.fullRoute,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val context = LocalContext.current
            ContactUsScreen(
                onNavigateToEmail = { email ->
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$email")
                        putExtra(Intent.EXTRA_SUBJECT, "Calorie Tracker feedback")
                        putExtra(Intent.EXTRA_TEXT, "Feedback text")
                    }
                    safeStartActivity(
                        context = context,
                        intent = emailIntent,
                        onException = { cause ->
                            onShowSnackbar(cause)
                        }
                    )
                },
                onNavigateToTelegram = { username ->
                    val telegramIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("tg://resolve?domain=$username")
                    }
                    safeStartActivity(
                        context = context,
                        intent = telegramIntent,
                        onException = { cause ->
                            onShowSnackbar(cause)
                        }
                    )
                },
                onNavigateToGithub = { username ->
                    val githubIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://github.com/$username")
                    }
                    safeStartActivity(
                        context = context,
                        intent = githubIntent,
                        onException = { cause ->
                            onShowSnackbar(cause)
                        }
                    )
                },
                onNavigateBack = navHostController::navigateUp,
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}

sealed class ProfileGraphScreens(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Profile : ProfileGraphScreens(route = "profile")

    data object UserInfo : ProfileGraphScreens(route = "user_info")

    data object CalorieIntake : ProfileGraphScreens(route = "calorie_intake")

    data object AboutApp : ProfileGraphScreens(route = "about_app")

    data object ContactUs : ProfileGraphScreens(route = "contact_us")

}