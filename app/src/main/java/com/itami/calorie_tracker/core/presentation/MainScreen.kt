package com.itami.calorie_tracker.core.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.AnimatedNavigationBar
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.BottomNavItem
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.ball_trajectory.StraightBall
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.StraightOutdent
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.shapeCornerRadius
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.utils.noRippleClickable
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.NavigationState
import com.itami.calorie_tracker.core.presentation.navigation.RootNavGraph
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.presentation.utils.BottomNavItem
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navState: NavigationState,
    startRoute: String = Graph.Auth.route,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
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
            BottomBar(navState = navState)
        }
    ) {
        it
        RootNavGraph(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues()),
            navState = navState,
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

@Composable
private fun BottomBar(
    navState: NavigationState,
) {
    val navItems = listOf(
        BottomNavItem.Recipes,
        BottomNavItem.Diary,
        BottomNavItem.Reports,
    )
    val backStackEntry by navState.navHostController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val selectedNavigationItemIndex = navItems.indexOfFirst { it.screenRoute == route }

    if (selectedNavigationItemIndex != -1) {
        AnimatedNavigationBar(
            modifier = Modifier
                .padding(
                    start = CalorieTrackerTheme.padding.small,
                    end = CalorieTrackerTheme.padding.small,
                    bottom = CalorieTrackerTheme.padding.small,
                )
                .height(84.dp)
                .fillMaxWidth(),
            selectedIndex = selectedNavigationItemIndex,
            barColor = CalorieTrackerTheme.colors.bottomBarContainer,
            ballColor = CalorieTrackerTheme.colors.primary,
            ballSize = 64.dp,
            ballAnimation = StraightBall(tween(400)),
            outdentAnimation = StraightOutdent(
                outdentWidth = 35.dp,
                outdentHeight = 20.dp,
                animationSpec = tween(400)
            ),
            cornerRadius = shapeCornerRadius(
                topLeft = 30.dp,
                topRight = 30.dp,
                bottomRight = 60.dp,
                bottomLeft = 60.dp
            ),
        ) {
            navItems.forEachIndexed { index, navItem ->
                val selected = selectedNavigationItemIndex == index
                BottomNavItem(
                    bottomNavItem = navItem,
                    isSelected = selected,
                    modifier = Modifier
                        .noRippleClickable {
                            if (!selected) {
                                navState.navigateToGraph(
                                    graph = navItem.screenRoute,
                                    popUpToId = navState.navHostController.graph.findStartDestination().id,
                                    inclusive = false,
                                    saveState = true,
                                    restoreState = true,
                                    launchSingleTop = true
                                )
                            }
                        }
                )
            }
        }
    }
}