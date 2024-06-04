package com.itami.calorie_tracker.authentication_feature.presentation.screens.login_email

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.use_case.LoginEmailUseCase
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginEmailViewModel @Inject constructor(
    private val loginEmailUseCase: LoginEmailUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<LoginEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginEmailState())
        private set

    fun onAction(action: LoginEmailAction) {
        when (action) {
            is LoginEmailAction.EmailInputChange -> {
                state = state.copy(
                    emailState = state.emailState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is LoginEmailAction.PasswordInputChange -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is LoginEmailAction.PasswordVisibilityIconClick -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        isPasswordVisible = !state.passwordState.isPasswordVisible
                    )
                )
            }

            is LoginEmailAction.LoginClick -> {
                login(
                    email = state.emailState.text,
                    password = state.passwordState.text
                )
            }

            is LoginEmailAction.ForgotPasswordClick -> {
                sendUiEvent(LoginEmailUiEvent.NavigateToForgotPassword)
            }

            is LoginEmailAction.NavigateBackClick -> {
                sendUiEvent(LoginEmailUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(uiEvent: LoginEmailUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun login(
        email: String,
        password: String,
    ) {
        state = state.copy(
            isLoading = true,
            emailState = state.emailState.copy(errorMessage = null),
            passwordState = state.passwordState.copy(errorMessage = null)
        )
        viewModelScope.launch {
            val loginResult = loginEmailUseCase.invoke(email, password)

            if (loginResult.emailException != null) {
                handleException(loginResult.emailException, null)
            }

            if (loginResult.passwordException != null) {
                handleException(loginResult.passwordException, null)
            }

            when (val response = loginResult.response) {
                is AppResponse.Success -> {
                    sendUiEvent(LoginEmailUiEvent.LoginSuccessful)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is UserCredentialsException.EmptyEmailFieldException -> {
                state = state.copy(
                    emailState = state.emailState.copy(
                        errorMessage = application.getString(R.string.error_empty_field)
                    )
                )
            }

            is UserCredentialsException.EmptyPasswordFieldException -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        errorMessage = application.getString(R.string.error_empty_field)
                    )
                )
            }

            is UserCredentialsException.InvalidEmailException -> {
                state = state.copy(
                    emailState = state.emailState.copy(
                        errorMessage = application.getString(R.string.error_invalid_email)
                    )
                )
            }

            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(LoginEmailUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(LoginEmailUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }
}