package com.itami.calorie_tracker.profile_feature.presentation.screens.about_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutAppViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent = Channel<AboutAppUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: AboutAppAction) {
        when(action) {
            is AboutAppAction.NavigateBackClick -> {
                sendUiEvent(AboutAppUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(event: AboutAppUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}