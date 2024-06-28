package com.itami.calorie_tracker.settings_feature.presentation.screens.account

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserCredentialsException
import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.DeleteAccountUseCase
import com.itami.calorie_tracker.core.domain.use_case.LogoutUseCase
import com.itami.calorie_tracker.core.domain.use_case.UpdateUserUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userManager: UserManager,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<AccountUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(AccountState())
        private set

    init {
        viewModelScope.launch {
            userManager.user.collect { state = state.copy(user = it) }
        }
    }

    fun onAction(action: AccountAction) {
        when (action) {
            is AccountAction.ChangePictureUri -> {
                state = state.copy(newProfilePictureUri = action.newUri)
            }

            is AccountAction.NameClick -> {
                state = state.copy(showNameSheetContent = true)
            }

            is AccountAction.ChangeName -> {
                state = state.copy(
                    newName = action.newName,
                    showNameSheetContent = false
                )
            }

            is AccountAction.ChangeProfilePicture -> {
                state = state.copy(newProfilePictureUri = action.uri)
            }

            is AccountAction.ChangePasswordClick -> {
                sendUiEvent(AccountUiEvent.NavigateToChangePassword)
            }

            is AccountAction.DeleteAccountClick -> {
                state = state.copy(showDeleteAccountDialog = true)
            }

            is AccountAction.LogoutClick -> {
                state = state.copy(showLogoutDialog = true)
            }

            is AccountAction.SaveClick -> {
                saveUser(
                    newName = state.newName,
                    newPictureUri = state.newProfilePictureUri
                )
            }

            is AccountAction.NavigateBackClick -> {
                sendUiEvent(AccountUiEvent.NavigateBack)
            }

            is AccountAction.DismissBottomSheet -> {
                state = state.copy(showNameSheetContent = false)
            }

            is AccountAction.ConfirmDeleteAccount -> {
                state = state.copy(showDeleteAccountDialog = false)
                deleteAccount()
            }

            is AccountAction.ConfirmLogout -> {
                state = state.copy(showLogoutDialog = false)
                logout()
            }

            is AccountAction.DenyDeleteAccount -> {
                state = state.copy(showDeleteAccountDialog = false)
            }

            is AccountAction.DenyLogout -> {
                state = state.copy(showLogoutDialog = false)
            }
        }
    }

    private fun saveUser(
        newName: String?,
        newPictureUri: Uri?,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            if (newName == null && newPictureUri == null) {
                sendUiEvent(AccountUiEvent.ShowSnackbar(application.getString(R.string.error_no_changes_found)))
                state = state.copy(isLoading = false)
                return@launch
            }

            val updateUser = UpdateUser(profilePictureUri = newPictureUri?.toString(), name = newName)
            val result = updateUserUseCase(updateUser)

            if (result.nameException != null) {
                handleException(result.nameException, null)
            }

            when (val response = result.response) {
                is AppResponse.Success -> {
                    sendUiEvent(AccountUiEvent.ShowSnackbar(application.getString(R.string.changes_have_been_saved)))
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun logout() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = logoutUseCase()) {
                is AppResponse.Success -> {
                    sendUiEvent(AccountUiEvent.LogoutSuccessful)
                }

                is AppResponse.Failed -> {
                    Unit
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun deleteAccount() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = deleteAccountUseCase()) {
                is AppResponse.Success -> {
                    sendUiEvent(AccountUiEvent.DeleteAccountSuccessful)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun sendUiEvent(event: AccountUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is UserCredentialsException.EmptyNameFieldException -> {
                val errorMessage = application.getString(R.string.error_empty_name)
                sendUiEvent(AccountUiEvent.ShowSnackbar(errorMessage))
            }

            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(AccountUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(AccountUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}