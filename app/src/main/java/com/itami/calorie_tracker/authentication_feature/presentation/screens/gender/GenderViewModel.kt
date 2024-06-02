package com.itami.calorie_tracker.authentication_feature.presentation.screens.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<GenderUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(GenderState())
        private set

    init {
        getGender()
    }

    fun onAction(action: GenderAction) {
        when (action) {
            is GenderAction.NavigateNextClick -> {
                saveGender(state.selectedGender)
            }

            is GenderAction.SelectGender -> {
                state = state.copy(selectedGender = action.gender)
            }

            is GenderAction.NavigateBackClick -> {
                sendUiEvent(GenderUiEvent.NavigateBack)
            }
        }
    }

    private fun getGender() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = state.copy(selectedGender = user.gender)
        }
    }

    private fun saveGender(gender: Gender) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            userManager.setGender(gender = gender)
            state = state.copy(isLoading = false)
            sendUiEvent(GenderUiEvent.GenderSaved)
        }
    }

    private fun sendUiEvent(event: GenderUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}