package com.itami.calorie_tracker.authentication_feature.presentation.screens.lifestyle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LifestyleViewModel @Inject constructor(
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<LifestyleUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LifestyleState())
        private set

    init {
        getGender()
    }

    fun onAction(action: LifestyleAction) {
        when (action) {
            is LifestyleAction.NavigateNextClick -> {
                saveLifestyle(state.selectedLifestyle)
            }

            is LifestyleAction.SelectLifestyle -> {
                state = state.copy(selectedLifestyle = action.lifestyle)
            }

            is LifestyleAction.NavigateBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(LifestyleUiEvent.NavigateBack)
                }
            }
        }
    }

    private fun getGender() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = state.copy(selectedLifestyle = user.lifestyle)
        }
    }

    private fun saveLifestyle(lifestyle: Lifestyle) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            userManager.setLifestyle(lifestyle = lifestyle)
            state = state.copy(isLoading = false)
            _uiEvent.send(LifestyleUiEvent.LifestyleSaved)
        }
    }
}