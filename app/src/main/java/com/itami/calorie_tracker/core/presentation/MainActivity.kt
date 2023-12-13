package com.itami.calorie_tracker.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil.ImageLoader
import com.itami.calorie_tracker.core.presentation.navigation.Graph
import com.itami.calorie_tracker.core.presentation.navigation.rememberNavigationState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.showSplash
        }
        setContent {
            val theme = mainViewModel.theme
            val startRoute = when {
                mainViewModel.isAuthenticated -> Graph.Diary.route
                mainViewModel.showOnboarding -> Graph.Onboarding.route
                else -> Graph.Auth.route
            }
            CalorieTrackerTheme(theme = theme) {
                val navState = rememberNavigationState()
                MainScreen(
                    startRoute = startRoute,
                    navState = navState,
                    imageLoader = imageLoader,
                )
            }
        }
    }
}
