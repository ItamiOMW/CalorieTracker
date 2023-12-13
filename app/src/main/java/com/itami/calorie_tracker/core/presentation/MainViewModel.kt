package com.itami.calorie_tracker.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.authentication_feature.domain.use_case.IsAuthenticatedUseCase
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.utils.AppResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager,
    private val isAuthenticatedUseCase: IsAuthenticatedUseCase,
) : ViewModel() {

    var theme by mutableStateOf(Theme.SYSTEM_THEME)
        private set

    var showSplash by mutableStateOf(true)
        private set

    var isAuthenticated by mutableStateOf(false)
        private set

    var showOnboarding by mutableStateOf(false)
        private set

    init {
        getIsDarkThemeState()
        getShowOnboardingState()
        getIsAuthenticatedState()
    }

    private fun getIsAuthenticatedState() {
        viewModelScope.launch {
            when (isAuthenticatedUseCase()) {
                is AppResponse.Success -> {
                    isAuthenticated = true
                    showSplash = false
                }

                is AppResponse.Failed -> {
                    isAuthenticated = false
                    showSplash = false
                }
            }
        }
    }

    private fun getShowOnboardingState() {
        viewModelScope.launch {
            val show = appSettingsManager.getShowOnboardingState()
            showOnboarding = show
            if (show) {
                showSplash = false
            }
        }
    }

    private fun getIsDarkThemeState() {
        viewModelScope.launch {
            appSettingsManager.theme.collect { theme ->
                this@MainViewModel.theme = theme
            }
        }
    }
}