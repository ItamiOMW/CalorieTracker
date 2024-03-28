package com.itami.calorie_tracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.itami.calorie_tracker.authentication_feature.presentation.authGraph
import com.itami.calorie_tracker.diary_feature.presentation.diaryGraph
import com.itami.calorie_tracker.onboarding_feature.presentation.onboardingGraph
import com.itami.calorie_tracker.profile_feature.presentation.profileGraph
import com.itami.calorie_tracker.recipes_feature.presentation.recipesGraph
import com.itami.calorie_tracker.reports_feature.presentation.reportsGraph


@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navState: NavigationState,
    startGraphRoute: String = Graph.Auth.route,
    onShowSnackbar: (message: String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navState.navHostController,
        route = Graph.Root.route,
        startDestination = startGraphRoute
    ) {
        onboardingGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
        authGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
        diaryGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
        recipesGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
        reportsGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
        profileGraph(
            navState = navState,
            onShowSnackbar = onShowSnackbar
        )
    }
}