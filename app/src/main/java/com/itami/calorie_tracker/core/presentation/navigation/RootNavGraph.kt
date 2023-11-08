package com.itami.calorie_tracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import coil.ImageLoader
import com.itami.calorie_tracker.authentication_feature.presentation.authGraph
import com.itami.calorie_tracker.diary_feature.presentation.diaryGraph
import com.itami.calorie_tracker.onboarding_feature.presentation.onboardingGraph


@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navState: NavigationState,
    startGraphRoute: String = Graph.AUTH.route,
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    onShowSnackbar: (message: String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navState.navHostController,
        route = Graph.ROOT.route,
        startDestination = startGraphRoute
    ) {
        onboardingGraph(
            navState = navState,
            imageLoader = imageLoader,
            isDarkTheme = isDarkTheme,
            onShowSnackbar = onShowSnackbar
        )
        authGraph(
            navState = navState,
            imageLoader = imageLoader,
            isDarkTheme = isDarkTheme,
            onShowSnackbar = onShowSnackbar
        )
        diaryGraph(
            navState = navState,
            imageLoader = imageLoader,
            isDarkTheme = isDarkTheme,
            onShowSnackbar = onShowSnackbar
        )
    }
}