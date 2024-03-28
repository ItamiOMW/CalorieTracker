package com.itami.calorie_tracker.recipes_feature.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen

fun NavGraphBuilder.recipesGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Recipes.route,
        startDestination = RecipesGraphScreen.Recipes.fullRoute
    ) {
        composable(route = RecipesGraphScreen.Recipes.fullRoute) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Recipes screen")
            }
        }
    }
}

sealed class RecipesGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Recipes : RecipesGraphScreen(route = "recipes")

}