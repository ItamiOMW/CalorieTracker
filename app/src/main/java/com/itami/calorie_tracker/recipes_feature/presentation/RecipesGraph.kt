package com.itami.calorie_tracker.recipes_feature.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.core.presentation.navigation.util.appendParams
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details.RecipeDetailsScreen
import com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes.RecipesScreen

fun NavGraphBuilder.recipesGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Recipes.route,
        startDestination = RecipesGraphScreen.Recipes.fullRoute
    ) {
        composable(
            route = RecipesGraphScreen.Recipes.fullRoute,
            enterTransition = {
                when (this.initialState.destination.parent?.route) {
                    Graph.Diary.route -> {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(250)
                        )
                    }
                    else -> {
                        fadeIn(animationSpec = tween(200))
                    }
                }
            },
            exitTransition = {
                if (this.targetState.destination.parent?.route == Graph.Diary.route) {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(250)
                    )
                } else {
                    fadeOut(tween(200))
                }
            }
        ) {
            RecipesScreen(
                onNavigateToRecipeDetail = { recipeId ->
                    navHostController.navigate(
                        RecipesGraphScreen.RecipeDetail.routeWithArgs(
                            recipeId
                        )
                    ) {
                        launchSingleTop = true
                    }
                },
                onNavigateToProfile = {
                    navHostController.navigate(Graph.Profile.route) {
                        launchSingleTop = true
                    }
                },
                onShowSnackbar = onShowSnackbar,
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
            RecipeDetailsScreen(
                onNavigateBack = {
                    navHostController.navigateUp()
                }
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