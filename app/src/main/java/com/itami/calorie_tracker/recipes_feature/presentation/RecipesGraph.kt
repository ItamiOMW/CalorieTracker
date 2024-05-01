package com.itami.calorie_tracker.recipes_feature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.core.presentation.navigation.util.appendParams
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details.RecipeDetailViewModel
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details.RecipeDetailsScreen
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes.RecipesScreen
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes.RecipesViewModel

fun NavGraphBuilder.recipesGraph(
    navState: NavigationState,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Recipes.route,
        startDestination = RecipesGraphScreen.Recipes.fullRoute
    ) {
        composable(route = RecipesGraphScreen.Recipes.fullRoute) {
            val viewModel: RecipesViewModel = hiltViewModel()
            RecipesScreen(
                onNavigateToRecipeDetail = { recipeId ->
                    navState.navigateToScreen(RecipesGraphScreen.RecipeDetail.routeWithArgs(recipeId))
                },
                onNavigateToProfile = {
                    navState.navigateToGraph(
                        graph = Graph.Profile.route,
                        popUpInclusive = false,
                        saveState = false,
                        restoreState = false
                    )
                },
                onShowSnackbar = onShowSnackbar,
                state = viewModel.state,
                uiEvent = viewModel.uiEvent,
                onEvent = viewModel::onEvent
            )
        }
        composable(
            route = RecipesGraphScreen.RecipeDetail.fullRoute,
            arguments = listOf(
                navArgument(RecipesGraphScreen.RECIPE_ID_ARG) {
                    type = NavType.IntType
                }
            )
        ) {
            val viewModel: RecipeDetailViewModel = hiltViewModel()
            RecipeDetailsScreen(
                onNavigateBack = {
                    navState.navigateBack()
                },
                state = viewModel.state,
                onEvent = viewModel::onEvent
            )
        }
    }
}

sealed class RecipesGraphScreen(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Recipes : RecipesGraphScreen(route = "recipes")

    data object RecipeDetail : RecipesGraphScreen(route = "recipe_detail", RECIPE_ID_ARG) {
        fun routeWithArgs(recipeId: Int): String {
            return route.appendParams(RECIPE_ID_ARG to recipeId)
        }
    }

    companion object {

        const val RECIPE_ID_ARG = "recipe_id_arg"
    }

}