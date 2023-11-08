package com.itami.calorie_tracker.diary_feature.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.ImageLoader
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.Screen


fun NavGraphBuilder.diaryGraph(
    navState: NavigationState,
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    onShowSnackbar: (message: String) -> Unit,
) {
    navigation(
        route = Graph.DIARY.route,
        startDestination = DiaryGraphScreens.Diary.fullRoute
    ) {
        composable(DiaryGraphScreens.Diary.fullRoute) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Diary screen")
            }
        }
    }
}

private sealed class DiaryGraphScreens(
    protected val route: String,
    vararg params: String,
) : Screen(route, *params) {

    data object Diary : DiaryGraphScreens(route = "diary")

}