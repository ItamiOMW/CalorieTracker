package com.itami.calorie_tracker.profile_feature.presentation.screens.contact_us

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.FeedbackMessageException
import com.itami.calorie_tracker.profile_feature.domain.use_case.SendFeedbackUseCase
import com.itami.calorie_tracker.core.presentation.state.StandardTextFieldState
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val sendFeedbackUseCase: SendFeedbackUseCase,
    private val application: Application,
) : ViewModel() {

    var state by mutableStateOf(ContactUsState())
        private set

    private val _uiEvent = Channel<ContactUsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: ContactUsAction) {
        when (action) {
            is ContactUsAction.NavigateBackClick -> {
                sendUiEvent(ContactUsUiEvent.NavigateBack)
            }

            is ContactUsAction.SendMessageClick -> {
                sendMessage(state.messageState.text)
            }

            is ContactUsAction.MessageValueChange -> {
                state = state.copy(
                    messageState = state.messageState.copy(
                        text = action.newValue,
                        errorMessage = null
                    )
                )
            }

            is ContactUsAction.EmailLinkClick -> {
                sendUiEvent(ContactUsUiEvent.NavigateToEmail(application.getString(R.string.developer_email_address)))
            }

            is ContactUsAction.GithubLinkClick -> {
                sendUiEvent(ContactUsUiEvent.NavigateToGithub(application.getString(R.string.developer_github_username)))
            }

            is ContactUsAction.TelegramLinkClick -> {
                sendUiEvent(ContactUsUiEvent.NavigateToTelegram(application.getString(R.string.developer_telegram_username)))
            }
        }
    }

    private fun sendMessage(message: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = sendFeedbackUseCase(message)) {
                is AppResponse.Success -> {
                    state = state.copy(messageState = StandardTextFieldState(text = ""))
                    sendUiEvent(ContactUsUiEvent.ShowSnackbar(application.getString(R.string.feedback_has_been_sent)))
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(event: ContactUsUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is FeedbackMessageException.ShortMessageException -> {
                val errorMessage = application.getString(R.string.error_short_message)
                state = state.copy(messageState = state.messageState.copy(errorMessage = errorMessage))
            }

            is FeedbackMessageException.LargeMessageException -> {
                val errorMessage = application.getString(R.string.error_large_message)
                state = state.copy(messageState = state.messageState.copy(errorMessage = errorMessage))
            }

            is FeedbackMessageException.EmptyMessageException -> {
                val errorMessage = application.getString(R.string.error_empty_field)
                state = state.copy(messageState = state.messageState.copy(errorMessage = errorMessage))
            }

            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(ContactUsUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(ContactUsUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}