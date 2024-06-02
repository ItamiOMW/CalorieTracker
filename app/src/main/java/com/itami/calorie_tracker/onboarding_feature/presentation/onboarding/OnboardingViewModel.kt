package com.itami.calorie_tracker.onboarding_feature.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appSettingsManager: AppSettingsManager
): ViewModel() {

    private val _uiEvent = Channel<OnboardingUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: OnboardingAction) {
        when(action) {
            is OnboardingAction.NavigateNextClick -> {
                changeShowOnboardingState(show = false)
            }

            is OnboardingAction.SkipClick -> {
                changeShowOnboardingState(show = false)
            }
        }
    }

    private fun changeShowOnboardingState(show: Boolean) {
        viewModelScope.launch {
            appSettingsManager.changeShowOnboardingState(show)
            _uiEvent.send(OnboardingUiEvent.ShowOnboardingStateSaved)
        }
    }

}