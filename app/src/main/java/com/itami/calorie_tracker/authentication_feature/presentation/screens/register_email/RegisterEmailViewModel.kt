package com.itami.calorie_tracker.authentication_feature.presentation.screens.register_email

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserEmail
import com.itami.calorie_tracker.authentication_feature.domain.use_case.RegisterEmailUseCase
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val registerEmailUseCase: RegisterEmailUseCase,
    private val userManager: UserManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<RegisterEmailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(RegisterEmailState())
        private set

    fun onAction(action: RegisterEmailAction) {
        when (action) {
            is RegisterEmailAction.NameInputChange -> {
                state = state.copy(
                    nameState = state.nameState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is RegisterEmailAction.EmailInputChange -> {
                state = state.copy(
                    emailState = state.emailState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is RegisterEmailAction.PasswordInputChange -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is RegisterEmailAction.PasswordVisibilityChange -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        isPasswordVisible = action.isVisible
                    )
                )
            }

            is RegisterEmailAction.Register -> {
                register(
                    name = state.nameState.text,
                    email = state.emailState.text,
                    password = state.passwordState.text,
                    profilePictureUri = state.profilePictureUri?.toString()
                )
            }

            is RegisterEmailAction.PictureUriChange -> {
                state = state.copy(profilePictureUri = action.uri)
            }
        }
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        profilePictureUri: String?,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val userToCreate = userManager.getUser()
            val createUserEmail = CreateUserEmail(
                email = email,
                password = password,
                name = name,
                profilePictureUri = profilePictureUri,
                age = userToCreate.age,
                heightCm = userToCreate.heightCm,
                weightGrams = userToCreate.weightGrams,
                lifestyle = userToCreate.lifestyle,
                gender = userToCreate.gender,
                weightGoal = userToCreate.weightGoal,
                dailyNutrientsGoal = userToCreate.dailyNutrientsGoal
            )
            val registerResult = registerEmailUseCase.invoke(createUserEmail)

            if (registerResult.nameException != null) {
                handleException(registerResult.nameException, null)
            }

            if (registerResult.emailException != null) {
                handleException(registerResult.emailException, null)
            }

            if (registerResult.passwordException != null) {
                handleException(registerResult.passwordException, null)
            }

            when (val response = registerResult.response) {
                is AppResponse.Success -> {
                    sendUiEvent(RegisterEmailUiEvent.RegisterSuccessful(email))
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(uiEvent: RegisterEmailUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is UserCredentialsException.EmptyNameFieldException -> {
                state = state.copy(
                    nameState = state.nameState.copy(
                        errorMessage = application.getString(R.string.error_empty_field)
                    )
                )
            }

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

            is UserCredentialsException.ShortPasswordException -> {
                state = state.copy(
                    passwordState = state.passwordState.copy(
                        errorMessage = application.getString(R.string.error_short_password)
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
                sendUiEvent(RegisterEmailUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(RegisterEmailUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}