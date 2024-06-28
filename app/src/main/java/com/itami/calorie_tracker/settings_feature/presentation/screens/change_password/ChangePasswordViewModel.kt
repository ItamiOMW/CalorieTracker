package com.itami.calorie_tracker.settings_feature.presentation.screens.change_password

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.domain.use_case.ChangePasswordUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<ChangePasswordUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ChangePasswordState())
        private set

    fun onAction(action: ChangePasswordAction) {
        when (action) {
            is ChangePasswordAction.NewPasswordChange -> {
                state = state.copy(
                    newPassword = state.newPassword.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ChangePasswordAction.OldPasswordChange -> {
                state = state.copy(
                    oldPassword = state.oldPassword.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ChangePasswordAction.RepeatNewPasswordChange -> {
                state = state.copy(
                    repeatNewPassword = state.repeatNewPassword.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ChangePasswordAction.ChangeNewPasswordVisibility -> {
                state = state.copy(
                    newPassword = state.newPassword.copy(
                        isPasswordVisible = !state.newPassword.isPasswordVisible
                    )
                )
            }

            is ChangePasswordAction.ChangeOldPasswordVisibility -> {
                state = state.copy(
                    oldPassword = state.oldPassword.copy(
                        isPasswordVisible = !state.oldPassword.isPasswordVisible
                    )
                )
            }

            is ChangePasswordAction.ChangeRepeatNewPasswordVisibility -> {
                state = state.copy(
                    repeatNewPassword = state.repeatNewPassword.copy(
                        isPasswordVisible = !state.repeatNewPassword.isPasswordVisible
                    )
                )
            }

            is ChangePasswordAction.ChangeClick -> {
                changePassword(
                    oldPassword = state.oldPassword.text,
                    newPassword = state.newPassword.text,
                    repeatNewPassword = state.repeatNewPassword.text
                )
            }

            is ChangePasswordAction.NavigateBackClick -> {
                sendUiEvent(ChangePasswordUiEvent.NavigateBack)
            }
        }
    }

    private fun sendUiEvent(event: ChangePasswordUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun changePassword(
        oldPassword: String,
        newPassword: String,
        repeatNewPassword: String,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val result = changePasswordUseCase(oldPassword, newPassword, repeatNewPassword)

            if (result.oldPasswordException != null) {
                val error = getPasswordErrorMessage(result.oldPasswordException)
                state = state.copy(oldPassword = state.oldPassword.copy(errorMessage = error))
            }

            if (result.newPasswordException != null) {
                val error = getPasswordErrorMessage(result.newPasswordException)
                state = state.copy(newPassword = state.newPassword.copy(errorMessage = error))
            }

            if (result.repeatPasswordException != null) {
                val error = getPasswordErrorMessage(result.repeatPasswordException)
                state = state.copy(repeatNewPassword = state.repeatNewPassword.copy(errorMessage = error))
            }

            when (val response = result.response) {
                is AppResponse.Success -> {
                    sendUiEvent(ChangePasswordUiEvent.ShowSnackbar(application.getString(R.string.password_has_been_changed)))
                    sendUiEvent(ChangePasswordUiEvent.PasswordChanged)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun getPasswordErrorMessage(exception: AppException): String {
        return when (exception) {
            is UserCredentialsException.PasswordsDoNotMatchException -> {
                application.getString(R.string.error_passwords_do_not_match)
            }

            is UserCredentialsException.ShortPasswordException -> {
                application.getString(R.string.error_short_password)
            }

            is UserCredentialsException.EmptyPasswordFieldException -> {
                application.getString(R.string.error_empty_field)
            }

            is UserCredentialsException.EmptyRepeatPasswordFieldException -> {
                application.getString(R.string.error_empty_field)
            }

            else -> {
                application.getString(R.string.error_unknown)
            }
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(ChangePasswordUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(ChangePasswordUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}