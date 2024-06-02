package com.itami.calorie_tracker.authentication_feature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.itami.calorie_tracker.authentication_feature.presentation.screens.age.AgeScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation.EmailActivationScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password.ForgotPasswordScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.gender.GenderScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.goal.GoalScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.height.HeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle.LifestyleScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email.LoginEmailScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients.RecommendedNutrientsScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email.RegisterEmailScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password.ResetPasswordScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password_success.ResetPasswordSuccessScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.weight.WeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome.WelcomeScreen
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.core.presentation.navigation.util.appendParams

fun NavGraphBuilder.authGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Auth.route,
        startDestination = AuthGraphScreen.Welcome.fullRoute
    ) {
        composable(route = AuthGraphScreen.Welcome.fullRoute) {
            WelcomeScreen(
                onStart = {
                    navState.navigateToScreen(route = AuthGraphScreen.Goal.fullRoute)
                },
                onGoogleLoginSuccessful = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true,
                    )
                },
                onNavigateToLoginEmail = {
                    navState.navigateToScreen(route = AuthGraphScreen.LoginEmail.fullRoute)
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Goal.fullRoute) {
            GoalScreen(
                onGoalSaved = {
                    navState.navigateToScreen(route = AuthGraphScreen.Gender.fullRoute)
                }
            )
        }
        composable(route = AuthGraphScreen.Gender.fullRoute) {
            GenderScreen(
                onGenderSaved = {
                    navState.navigateToScreen(route = AuthGraphScreen.Lifestyle.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                }
            )
        }
        composable(route = AuthGraphScreen.Lifestyle.fullRoute) {
            LifestyleScreen(
                onLifestyleSaved = {
                    navState.navigateToScreen(AuthGraphScreen.Height.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                }
            )
        }
        composable(route = AuthGraphScreen.Height.fullRoute) {
            HeightScreen(
                onHeightSaved = {
                    navState.navigateToScreen(AuthGraphScreen.Weight.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Weight.fullRoute) {
            WeightScreen(
                onWeightSaved = {
                    navState.navigateToScreen(route = AuthGraphScreen.Age.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Age.fullRoute) {
            AgeScreen(
                onAgeSaved = {
                    navState.navigateToScreen(route = AuthGraphScreen.RecommendedNutrients.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.RecommendedNutrients.fullRoute) {
            RecommendedNutrientsScreen(
                onGoogleRegisterSuccess = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true
                    )
                },
                onNavigateToRegisterEmail = {
                    navState.navigateToScreen(route = AuthGraphScreen.RegisterEmail.fullRoute)
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.LoginEmail.fullRoute) {
            LoginEmailScreen(
                onLoginSuccess = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true,
                    )
                },
                onNavigateToForgotPassword = {
                    navState.navigateToScreen(route = AuthGraphScreen.ForgotPassword.fullRoute)
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.ForgotPassword.fullRoute) {
            ForgotPasswordScreen(
                onResetCodeSent = { email ->
                    navState.navigateToScreen(
                        route = AuthGraphScreen.ResetPassword.routeWithArgs(
                            email
                        )
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = AuthGraphScreen.ResetPassword.fullRoute,
            arguments = listOf(
                navArgument(AuthGraphScreen.EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            ResetPasswordScreen(
                onPasswordResetSuccess = {
                    navState.navigateToScreen(
                        route = AuthGraphScreen.ResetPasswordSuccess.fullRoute,
                        popUpToRoute = AuthGraphScreen.LoginEmail.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.ResetPasswordSuccess.fullRoute) {
            ResetPasswordSuccessScreen(
                onNavigateBack = {
                    navState.navigateBack(AuthGraphScreen.LoginEmail.fullRoute)
                },
                onNavigateToLogin = {
                    navState.navigateBack(AuthGraphScreen.LoginEmail.fullRoute)
                }
            )
        }
        composable(route = AuthGraphScreen.RegisterEmail.fullRoute) {
            RegisterEmailScreen(
                onEmailRegisterSuccess = { email ->
                    navState.navigateToScreen(
                        route = AuthGraphScreen.EmailActivation.routeWithArgs(email),
                        popUpToRoute = AuthGraphScreen.Welcome.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = AuthGraphScreen.EmailActivation.fullRoute,
            arguments = listOf(
                navArgument(AuthGraphScreen.EMAIL_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            EmailActivationScreen(
                onNavigateToLogin = {
                    navState.navigateToScreen(
                        route = AuthGraphScreen.LoginEmail.fullRoute,
                        popUpToRoute = AuthGraphScreen.Welcome.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar
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

    data object LoginEmail : AuthGraphScreen(route = "login_email")

    data object ForgotPassword : AuthGraphScreen(route = "forgot_password")

    data object ResetPassword : AuthGraphScreen(route = "reset_password", EMAIL_ARG) {
        fun routeWithArgs(email: String): String {
            return route.appendParams(EMAIL_ARG to email)
        }
    }

    data object ResetPasswordSuccess : AuthGraphScreen(route = "reset_password")

    data object RegisterEmail : AuthGraphScreen(route = "register_email")

    data object EmailActivation : AuthGraphScreen(route = "email_activation", EMAIL_ARG) {
        fun routeWithArgs(email: String): String {
            return route.appendParams(EMAIL_ARG to email)
        }
    }

    companion object {

        const val EMAIL_ARG = "email_arg"

    }

}