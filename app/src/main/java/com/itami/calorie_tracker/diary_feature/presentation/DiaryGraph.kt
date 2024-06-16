package com.itami.calorie_tracker.diary_feature.presentation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.Screen
import com.itami.calorie_tracker.core.presentation.navigation.util.appendParams
import com.itami.calorie_tracker.core.presentation.navigation.util.navigateForResult
import com.itami.calorie_tracker.core.presentation.navigation.util.popBackStackWithResult
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.presentation.screens.diary.DiaryScreen
import com.itami.calorie_tracker.diary_feature.presentation.screens.meal.MealScreen
import com.itami.calorie_tracker.diary_feature.presentation.screens.new_meal.NewMealScreen
import com.itami.calorie_tracker.diary_feature.presentation.screens.search_food.SearchFoodScreen

fun NavGraphBuilder.diaryGraph(
    navHostController: NavHostController,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.Diary.route,
        startDestination = DiaryGraphScreens.Diary.fullRoute
    ) {
        composable(DiaryGraphScreens.Diary.fullRoute) {
            DiaryScreen(
                onNavigateToMeal = { mealId ->
                    navHostController.navigate(DiaryGraphScreens.Meal.routeWithArgs(mealId)) {
                        launchSingleTop = true
                    }
                },
                onNavigateToNewMeal = { datetime ->
                    val encodedDate = Uri.encode(datetime)
                    navHostController.navigate(DiaryGraphScreens.NewMeal.routeWithArgs(encodedDate)) {
                        launchSingleTop = true
                    }
                },
                onNavigateToProfile = {
                    navHostController.navigate(Graph.Profile.route)
                },
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(
            route = DiaryGraphScreens.NewMeal.fullRoute,
            arguments = listOf(
                navArgument(DiaryGraphScreens.ENCODED_DATETIME_ARG) {
                    type = NavType.StringType
                    defaultValue = DateTimeUtil.getCurrentDateTimeString()
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                ) + fadeIn(animationSpec = tween(750))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 2
                    }
                ) + fadeOut(animationSpec = tween(750))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 2
                    }
                ) + fadeOut(animationSpec = tween(750))
            }
        ) {
            NewMealScreen(
                onNavigateSearchFood = { navCallback ->
                    navHostController.navigateForResult(
                        route = DiaryGraphScreens.SearchFood.fullRoute,
                        navResultCallback = navCallback,
                    )
                },
                onMealSaved = {
                    navHostController.navigateUp()
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(
            route = DiaryGraphScreens.Meal.fullRoute,
            arguments = listOf(
                navArgument(DiaryGraphScreens.MEAL_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = Constants.UNKNOWN_ID
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                ) + fadeIn(animationSpec = tween(750))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 2
                    }
                ) + fadeOut(animationSpec = tween(750))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    targetOffset = {
                        it / 2
                    }
                ) + fadeOut(animationSpec = tween(750))
            }
        ) {
            MealScreen(
                onNavigateSearchFood = { navCallback ->
                    navHostController.navigateForResult(
                        route = DiaryGraphScreens.SearchFood.fullRoute,
                        navResultCallback = navCallback,
                    )
                },
                onMealSaved = {
                    navHostController.navigateUp()
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar,
            )
        }
        composable(
            route = DiaryGraphScreens.SearchFood.fullRoute,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, animationSpec = tween(350)
                ) + fadeIn(animationSpec = tween(750))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(350)
                ) + fadeOut(animationSpec = tween(750))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, animationSpec = tween(350)
                ) + fadeOut(animationSpec = tween(750))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, animationSpec = tween(350)
                ).plus(fadeIn(tween(750)))
            },
        ) {
            SearchFoodScreen(
                onNavigateBackWithFood = { consumedFood ->
                    navHostController.popBackStackWithResult(consumedFood)
                },
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}

sealed class DiaryGraphScreens(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Diary : DiaryGraphScreens(route = "diary")

    data object NewMeal : DiaryGraphScreens(route = "new_meal", ENCODED_DATETIME_ARG) {
        fun routeWithArgs(date: String): String {
            return route.appendParams(ENCODED_DATETIME_ARG to date)
        }
    }

    data object Meal : DiaryGraphScreens(route = "meal", MEAL_ID_ARG) {
        fun routeWithArgs(mealId: Int): String {
            return route.appendParams(MEAL_ID_ARG to mealId)
        }
    }

    data object SearchFood : DiaryGraphScreens(route = "search_food")

    companion object {
        const val ENCODED_DATETIME_ARG = "encoded_date_arg"
        const val MEAL_ID_ARG = "meal_id_arg"
    }

}