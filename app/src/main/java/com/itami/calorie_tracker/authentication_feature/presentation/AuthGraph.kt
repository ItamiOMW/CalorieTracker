package com.itami.calorie_tracker.authentication_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.itami.calorie_tracker.authentication_feature.presentation.screens.age.AgeScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.age.AgeViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation.EmailActivationScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation.EmailActivationViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password.ForgotPasswordScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password.ForgotPasswordViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.gender.GenderScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.gender.GenderViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.goal.GoalScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.goal.GoalViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.height.HeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.height.HeightViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle.LifestyleScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle.LifestyleViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email.LoginEmailScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email.LoginEmailViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients.RecommendedNutrientsScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients.RecommendedNutrientsViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email.RegisterEmailScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email.RegisterEmailViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password.ResetPasswordScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password.ResetPasswordViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password_success.ResetPasswordSuccessScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.weight.WeightScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.weight.WeightViewModel
import com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome.WelcomeScreen
import com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome.WelcomeViewModel
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
        composable(
            route = AuthGraphScreen.Welcome.fullRoute,
        ) {
            val viewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(
                onNavigateToDiary = {
                    navState.navigateToGraph(
                        graph = Graph.Diary.route,
                        popUpInclusive = true,
                    )
                },
                onNavigateToLoginEmail = {
                    navState.navigateToScreen(route = AuthGraphScreen.LoginEmail.fullRoute)
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
                        popUpInclusive = true
                    )
                },
                onNavigateToRegisterEmail = {
                    navState.navigateToScreen(route = AuthGraphScreen.RegisterEmail.fullRoute)
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(route = AuthGraphScreen.LoginEmail.fullRoute) {
            val viewModel: LoginEmailViewModel = hiltViewModel()
            LoginEmailScreen(
                onNavigateToDiary = {
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
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction
            )
        }
        composable(route = AuthGraphScreen.ForgotPassword.fullRoute) {
            val viewModel: ForgotPasswordViewModel = hiltViewModel()
            ForgotPasswordScreen(
                onNavigateToResetPassword = { email ->
                    navState.navigateToScreen(
                        route = AuthGraphScreen.ResetPassword.routeWithArgs(
                            email
                        )
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction
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
            val viewModel: ResetPasswordViewModel = hiltViewModel()
            ResetPasswordScreen(
                onNavigateToResetPasswordSuccess = {
                    navState.navigateToScreen(
                        route = AuthGraphScreen.ResetPasswordSuccess.fullRoute,
                        popUpToRoute = AuthGraphScreen.LoginEmail.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction
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
            val viewModel: RegisterEmailViewModel = hiltViewModel()
            RegisterEmailScreen(
                onNavigateToEmailConfirmation = { email ->
                    navState.navigateToScreen(
                        route = AuthGraphScreen.EmailActivation.routeWithArgs(email),
                        popUpToRoute = AuthGraphScreen.Welcome.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction
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
            val viewModel: EmailActivationViewModel = hiltViewModel()
            EmailActivationScreen(
                onNavigateToLoginEmail = {
                    navState.navigateToScreen(
                        route = AuthGraphScreen.LoginEmail.fullRoute,
                        popUpToRoute = AuthGraphScreen.Welcome.fullRoute
                    )
                },
                onNavigateBack = {
                    navState.navigateBack()
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction
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