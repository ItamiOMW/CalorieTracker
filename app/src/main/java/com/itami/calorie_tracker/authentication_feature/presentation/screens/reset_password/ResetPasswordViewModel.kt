package com.itami.calorie_tracker.authentication_feature.presentation.screens.reset_password

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.use_case.ResetPasswordUseCase
import com.itami.calorie_tracker.authentication_feature.domain.use_case.SendPasswordResetCodeUseCase
import com.itami.calorie_tracker.authentication_feature.presentation.AuthGraphScreen
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val sendPasswordResetCodeUseCase: SendPasswordResetCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<ResetPasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ResetPasswordState())
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>(AuthGraphScreen.EMAIL_ARG)?.let { email ->
                state = state.copy(email = email)
            } ?: throw RuntimeException("Email argument was not passed")
        }
    }

    fun onAction(action: ResetPasswordAction) {
        when (action) {
            is ResetPasswordAction.CodeInputChange -> {
                state = state.copy(
                    codeState = state.codeState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ResetPasswordAction.PasswordInputChange -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ResetPasswordAction.PasswordVisibilityChange -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        isPasswordVisible = action.isVisible
                    )
                )
            }

            is ResetPasswordAction.RepeatPasswordInputChange -> {
                state = state.copy(
                    repeatPasswordState = state.repeatPasswordState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ResetPasswordAction.RepeatPasswordVisibilityChange -> {
                state = state.copy(
                    repeatPasswordState = state.repeatPasswordState.copy(
                        isPasswordVisible = action.isVisible
                    )
                )
            }

            is ResetPasswordAction.ResendResetCodeClick -> {
                sendPasswordResetEmail(state.email)
            }

            is ResetPasswordAction.ResetPasswordClick -> {
                resetPassword(
                    password = state.passwordState.text,
                    repeatPassword = state.repeatPasswordState.text,
                    email = state.email,
                    code = state.codeState.text.toInt()
                )
            }

            is ResetPasswordAction.NavigateBackClick -> {
                sendUiEvent(ResetPasswordUiEvent.NavigateBack)
            }
        }
    }

    private fun resetPassword(
        password: String,
        repeatPassword: String,
        email: String,
        code: Int,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val resetPasswordResult = resetPasswordUseCase(
                resetCode = code,
                email = email,
                newPassword = password,
                repeatNewPassword = repeatPassword
            )

            if (resetPasswordResult.passwordException != null) {
                handleException(resetPasswordResult.passwordException, null)
            }

            if (resetPasswordResult.repeatPasswordException != null) {
                handleException(resetPasswordResult.repeatPasswordException, null)
            }

            if (resetPasswordResult.codeException != null) {
                handleException(resetPasswordResult.codeException, null)
            }

            when (val response = resetPasswordResult.response) {
                is AppResponse.Success -> {
                    sendUiEvent(ResetPasswordUiEvent.PasswordResetSuccessful)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = sendPasswordResetCodeUseCase(email)) {
                is AppResponse.Success -> {
                    sendUiEvent(ResetPasswordUiEvent.ShowSnackbar(application.getString(R.string.reset_code_has_been_sent)))
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(uiEvent: ResetPasswordUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is UserCredentialsException.EmptyPasswordFieldException -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        errorMessage = application.getString(R.string.error_empty_field)
                    )
                )
            }

            is UserCredentialsException.EmptyRepeatPasswordFieldException -> {
                state = state.copy(
                    repeatPasswordState = state.repeatPasswordState.copy(
                        errorMessage = application.getString(R.string.error_empty_field)
                    )
                )
            }

            is UserCredentialsException.ShortPasswordException -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        errorMessage = application.getString(R.string.error_short_password)
                    )
                )
            }

            is UserCredentialsException.PasswordsDoNotMatchException -> {
                state = state.copy(
                    repeatPasswordState = state.repeatPasswordState.copy(
                        errorMessage = application.getString(R.string.error_passwords_do_not_match)
                    )
                )
            }

            is UserCredentialsException.InvalidResetCodeException -> {
                state = state.copy(
                    codeState = state.codeState.copy(
                        errorMessage = application.getString(R.string.error_invalid_reset_code)
                    )
                )
            }

            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(ResetPasswordUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(ResetPasswordUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}