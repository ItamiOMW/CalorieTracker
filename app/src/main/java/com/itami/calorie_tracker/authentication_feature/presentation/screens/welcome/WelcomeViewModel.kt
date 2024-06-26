package com.itami.calorie_tracker.authentication_feature.presentation.screens.welcome

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.use_case.LoginGoogleUseCase
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val loginGoogleUseCase: LoginGoogleUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<WelcomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(WelcomeState())
        private set

    fun onAction(action: WelcomeAction) {
        when (action) {
            is WelcomeAction.GoogleIdTokenReceived -> {
                signInWithGoogle(idToken = action.idToken)
            }

            is WelcomeAction.GoogleIdTokenNoteReceived -> {
                state = state.copy(showGoogleOneTap = false)
                action.cause?.let { sendUiEvent(WelcomeUiEvent.ShowSnackbar(it)) }
            }

            is WelcomeAction.SignInWithEmailClick -> {
                sendUiEvent(WelcomeUiEvent.NavigateToLoginEmail)
            }

            is WelcomeAction.StartClick -> {
                sendUiEvent(WelcomeUiEvent.Start)
            }

            is WelcomeAction.SignInWithGoogleClick -> {
                state = state.copy(showGoogleOneTap = true)
            }
        }
    }

    private fun sendUiEvent(uiEvent: WelcomeUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun signInWithGoogle(idToken: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = loginGoogleUseCase(idToken)) {
                is AppResponse.Success -> {
                    sendUiEvent(WelcomeUiEvent.GoogleLoginSuccessful)
                }

                is AppResponse.Failed -> {
                    handleException(
                        appException = response.appException,
                        message = response.message
                    )
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                sendUiEvent(
                    WelcomeUiEvent.ShowSnackbar(
                        application.getString(R.string.error_network)
                    )
                )
            }

            is AppException.UnauthorizedException -> {
                sendUiEvent(
                    WelcomeUiEvent.ShowSnackbar(
                        message = message
                            ?: application.getString(R.string.error_google_unauthorized)
                    )
                )
            }

            else -> {
                sendUiEvent(
                    WelcomeUiEvent.ShowSnackbar(
                        message = message ?: application.getString(R.string.error_unknown)
                    )
                )
            }
        }
    }

}