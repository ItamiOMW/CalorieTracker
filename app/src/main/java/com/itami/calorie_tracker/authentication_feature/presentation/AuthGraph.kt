package com.itami.calorie_tracker.authentication_feature.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
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
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.core.presentation.navigation.util.appendParams

fun NavGraphBuilder.authGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Auth.route,
        startDestination = AuthGraphScreen.Welcome.fullRoute
    ) {
        composable(route = AuthGraphScreen.Welcome.fullRoute) {
            WelcomeScreen(
                onStart = {
                    navHostController.navigate(AuthGraphScreen.Goal.fullRoute)
                },
                onNavigateToLoginEmail = {
                    navHostController.navigate(AuthGraphScreen.LoginEmail.fullRoute)
                },
                onGoogleLoginSuccessful = {
                    navHostController.navigate(Graph.Diary.route) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Goal.fullRoute) {
            GoalScreen(
                onGoalSaved = {
                    navHostController.navigate(AuthGraphScreen.Gender.fullRoute)
                }
            )
        }
        composable(route = AuthGraphScreen.Gender.fullRoute) {
            GenderScreen(
                onGenderSaved = {
                    navHostController.navigate(AuthGraphScreen.Lifestyle.fullRoute)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = AuthGraphScreen.Lifestyle.fullRoute) {
            LifestyleScreen(
                onLifestyleSaved = {
                    navHostController.navigate(AuthGraphScreen.Height.fullRoute)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = AuthGraphScreen.Height.fullRoute) {
            HeightScreen(
                onHeightSaved = {
                    navHostController.navigate(AuthGraphScreen.Weight.fullRoute)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Weight.fullRoute) {
            WeightScreen(
                onWeightSaved = {
                    navHostController.navigate(AuthGraphScreen.Age.fullRoute)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.Age.fullRoute) {
            AgeScreen(
                onAgeSaved = {
                    navHostController.navigate(AuthGraphScreen.RecommendedNutrients.fullRoute)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.RecommendedNutrients.fullRoute) {
            RecommendedNutrientsScreen(
                onGoogleRegisterSuccess = {
                    navHostController.navigate(Graph.Diary.route) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegisterEmail = {
                    navHostController.navigate(AuthGraphScreen.RegisterEmail.fullRoute) {
                        popUpTo(AuthGraphScreen.RecommendedNutrients.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.LoginEmail.fullRoute) {
            LoginEmailScreen(
                onLoginSuccess = {
                    navHostController.navigate(Graph.Diary.route) {
                        popUpTo(Graph.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToForgotPassword = {
                    navHostController.navigate(route = AuthGraphScreen.ForgotPassword.fullRoute) {
                        popUpTo(AuthGraphScreen.LoginEmail.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.ForgotPassword.fullRoute) {
            ForgotPasswordScreen(
                onResetCodeSent = { email ->
                    navHostController.navigate(AuthGraphScreen.ResetPassword.routeWithArgs(email)) {
                        popUpTo(AuthGraphScreen.ForgotPassword.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
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
                    navHostController.navigate(AuthGraphScreen.ResetPasswordSuccess.fullRoute) {
                        popUpTo(AuthGraphScreen.LoginEmail.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(route = AuthGraphScreen.ResetPasswordSuccess.fullRoute) {
            ResetPasswordSuccessScreen(
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onNavigateToLogin = {
                    navHostController.navigateUp()
                }
            )
        }
        composable(route = AuthGraphScreen.RegisterEmail.fullRoute) {
            RegisterEmailScreen(
                onEmailRegisterSuccess = { email ->
                    navHostController.navigate(AuthGraphScreen.EmailActivation.routeWithArgs(email)) {
                        popUpTo(AuthGraphScreen.Welcome.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
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
                    navHostController.navigate(AuthGraphScreen.LoginEmail.fullRoute) {
                        popUpTo(AuthGraphScreen.Welcome.fullRoute) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onNavigateBack = {
                    navHostController.navigateUp()
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