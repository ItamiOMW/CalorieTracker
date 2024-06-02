package com.itami.calorie_tracker.authentication_feature.presentation.screens.email_activation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.use_case.ResendActivationEmailUseCase
import com.itami.calorie_tracker.authentication_feature.presentation.AuthGraphScreen
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailActivationViewModel @Inject constructor(
    private val resendActivationEmailUseCase: ResendActivationEmailUseCase,
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiEvent = Channel<EmailActivationUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(EmailActivationState())
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(AuthGraphScreen.EMAIL_ARG)?.let { email ->
                state = state.copy(email = email)
            } ?: throw RuntimeException("Email argument was not passed.")
        }
    }

    fun onAction(action: EmailActivationAction) {
        when (action) {
            is EmailActivationAction.OnResendConfirmationEmailClick -> {
                resendEmail(state.email)
            }

            is EmailActivationAction.OnNavigateBackClick -> {
                sendUiEvent(EmailActivationUiEvent.NavigateToLogin)
            }

            is EmailActivationAction.OnGoToLoginClick -> {
                sendUiEvent(EmailActivationUiEvent.NavigateToLogin)
            }
        }
    }

    private fun resendEmail(
        email: String,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = resendActivationEmailUseCase(email)) {
                is AppResponse.Success -> {
                    sendUiEvent(EmailActivationUiEvent.ShowSnackbar(application.getString(R.string.sent_activation_link_success)))
                }

                is AppResponse.Failed -> {
                    handleException(exception = response.appException, message = response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(uiEvent: EmailActivationUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(EmailActivationUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(EmailActivationUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}