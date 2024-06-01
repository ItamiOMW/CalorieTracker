package com.itami.calorie_tracker.authentication_feature.presentation.screens.forgot_password

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.use_case.SendPasswordResetCodeUseCase
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val sendPasswordResetCodeUseCase: SendPasswordResetCodeUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<ForgotPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ForgotPasswordState())
        private set

    fun onAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.EmailInputChange -> {
                state = state.copy(
                    emailState = state.emailState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ForgotPasswordAction.SendPasswordResetCode -> {
                sendPasswordResetEmail(email = state.emailState.text)
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = sendPasswordResetCodeUseCase(email)) {
                is AppResponse.Success -> {
                    sendUiEvent(ForgotPasswordUiEvent.ResetCodeSentSuccessfully(email))
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(uiEvent: ForgotPasswordUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(ForgotPasswordUiEvent.ShowSnackbar(errorMessage))
            }

            is UserCredentialsException.EmptyEmailFieldException -> {
                val errorMessage = application.getString(R.string.error_empty_field)
                state = state.copy(emailState = state.emailState.copy(errorMessage = errorMessage))
            }

            is UserCredentialsException.InvalidEmailException -> {
                val errorMessage = application.getString(R.string.error_invalid_email)
                state = state.copy(emailState = state.emailState.copy(errorMessage = errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(ForgotPasswordUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}