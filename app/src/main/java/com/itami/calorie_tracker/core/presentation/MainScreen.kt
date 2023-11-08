package com.itami.calorie_tracker.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.RootNavGraph
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    isDarkTheme: Boolean,
    navState: NavigationState,
    imageLoader: ImageLoader,
    startRoute: String = Graph.AUTH.route,
) {
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(snackbarHostState) { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        modifier = Modifier
                            .padding(bottom = CalorieTrackerTheme.padding.extraLarge),
                        shape = CalorieTrackerTheme.shapes.small,
                        containerColor = CalorieTrackerTheme.colors.surfacePrimary,
                        contentColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                        dismissActionContentColor = CalorieTrackerTheme.colors.onSurfaceVariant
                    )
                }
            }
        },
        bottomBar = {

        }
    ) {
        RootNavGraph(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navState = navState,
            imageLoader = imageLoader,
            isDarkTheme = isDarkTheme,
            startGraphRoute = startRoute,
            onShowSnackbar = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )
    }
}