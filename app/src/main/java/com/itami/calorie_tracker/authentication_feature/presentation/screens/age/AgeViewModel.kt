package com.itami.calorie_tracker.authentication_feature.presentation.screens.age

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.SetAgeUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val setAgeUseCase: SetAgeUseCase,
    private val userManager: UserManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<AgeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(AgeState())
        private set

    init {
        getAge()
    }

    fun onAction(action: AgeAction) {
        when (action) {
            is AgeAction.AgeValueChange -> {
                state = state.copy(age = action.age)
            }

            is AgeAction.NavigateNextClick -> {
                saveAge(age = state.age.toInt())
            }

            is AgeAction.NavigateBackClick -> {
                sendUiEvent(AgeUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(uiEvent: AgeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun getAge() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = state.copy(age = user.age.toString())
        }
    }

    private fun saveAge(age: Int) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val result = setAgeUseCase(age = age)) {
                is AppResponse.Success -> {
                    sendUiEvent(uiEvent = AgeUiEvent.AgeSaved)
                }

                is AppResponse.Failed -> {
                    handleException(appException = result.appException, message = result.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is UserInfoException.InvalidAgeException -> {
                sendUiEvent(
                    uiEvent = AgeUiEvent.ShowSnackbar(
                        message = application.getString(R.string.error_invalid_age)
                    )
                )
            }

            else -> {
                sendUiEvent(
                    uiEvent = AgeUiEvent.ShowSnackbar(
                        message = message ?: application.getString(R.string.error_unknown)
                    )
                )
            }
        }
    }
}