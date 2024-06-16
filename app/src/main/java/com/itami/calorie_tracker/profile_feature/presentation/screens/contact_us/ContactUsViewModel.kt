package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(): ViewModel() {

    private val _uiEvent = Channel<ContactUsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: ContactUsAction) {
        when(action) {
            is ContactUsAction.NavigateBackClick -> {
                sendUiEvent(ContactUsUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(event: ContactUsUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}