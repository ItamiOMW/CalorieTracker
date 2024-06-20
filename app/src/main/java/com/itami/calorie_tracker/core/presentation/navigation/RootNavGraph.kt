package com.itami.calorie_tracker.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.itami.calorie_tracker.authentication_feature.presentation.authGraph
import com.itami.calorie_tracker.diary_feature.presentation.diaryGraph
import com.itami.calorie_tracker.onboarding_feature.presentation.onboardingGraph
import com.itami.calorie_tracker.profile_feature.presentation.profileGraph
import com.itami.calorie_tracker.recipes_feature.presentation.recipesGraph
import com.itami.calorie_tracker.reports_feature.presentation.reportsGraph


@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startGraphRoute: String = Graph.Auth.route,
    onShowSnackbar: (message: String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        route = Graph.Root.route,
        startDestination = startGraphRoute,
    ) {
        onboardingGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
        authGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
        diaryGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
        recipesGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
        reportsGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
        profileGraph(
            navHostController = navHostController,
            onShowSnackbar = onShowSnackbar
        )
    }
}