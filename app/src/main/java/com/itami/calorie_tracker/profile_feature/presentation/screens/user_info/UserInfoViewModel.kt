package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.exceptions.UserInfoException
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.CalculateNutrientsUseCase
import com.itami.calorie_tracker.core.domain.use_case.UpdateUserUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userManager: UserManager,
    private val appSettingsManager: AppSettingsManager,
    private val calculateNutrientsUseCase: CalculateNutrientsUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<UserInfoUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(UserInfoState())
        private set

    private lateinit var originalUser: User

    init {
        getWeightUnit()
        getHeightUnit()
        getUser()
    }

    fun onAction(action: UserInfoAction) {
        when (action) {
            is UserInfoAction.NavigateBackClick -> {
                sendUiEvent(UserInfoUiEvent.NavigateBack)
            }

            is UserInfoAction.SaveClick -> {
                state = state.copy(showSaveDialog = true)
            }

            is UserInfoAction.SaveUserConfirm -> {
                state = state.copy(showSaveDialog = false)
                saveUserInfo(
                    updatedUser = state.user,
                    originalUser = originalUser,
                    updateNutrients = action.updateNutrients
                )
            }

            is UserInfoAction.SaveUserDeny -> {
                state = state.copy(showSaveDialog = false)
            }

            is UserInfoAction.AgeFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.AGE)
            }

            is UserInfoAction.GenderFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.GENDER)
            }

            is UserInfoAction.GoalFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.GOAL)
            }

            is UserInfoAction.HeightFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.HEIGHT)
            }

            is UserInfoAction.LifestyleFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.LIFESTYLE)
            }

            is UserInfoAction.WeightFieldClick -> {
                state = state.copy(sheetContent = UserInfoScreenSheetContent.WEIGHT)
            }

            is UserInfoAction.DismissBottomSheet -> {
                state = state.copy(sheetContent = null)
            }

            is UserInfoAction.ChangeAge -> {
                state = state.copy(
                    user = state.user.copy(age = action.age),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeGender -> {
                state = state.copy(
                    user = state.user.copy(gender = action.gender),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeGoal -> {
                state = state.copy(
                    user = state.user.copy(weightGoal = action.goal),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeHeight -> {
                state = state.copy(
                    user = state.user.copy(heightCm = action.heightCm),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeLifestyle -> {
                state = state.copy(
                    user = state.user.copy(lifestyle = action.lifestyle),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeWeight -> {
                state = state.copy(
                    user = state.user.copy(weightGrams = action.weightGrams),
                    sheetContent = null
                )
            }

            is UserInfoAction.ChangeWeightUnit -> {
                changeWeightUnit(action.weightUnit)
            }

            is UserInfoAction.ChangeHeightUnit -> {
                changeHeightUnit(action.heightUnit)
            }
        }
    }

    private fun saveUserInfo(
        updatedUser: User,
        originalUser: User,
        updateNutrients: Boolean,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            if (updatedUser == originalUser) {
                sendUiEvent(UserInfoUiEvent.UserInfoSaved)
                return@launch
            }

            val userToSave = if (updateNutrients) {
                val recommendedNutrients = calculateNutrientsUseCase(
                    age = updatedUser.age,
                    heightCm = updatedUser.heightCm,
                    weightGrams = updatedUser.weightGrams,
                    gender = updatedUser.gender,
                    weightGoal = updatedUser.weightGoal,
                    lifestyle = updatedUser.lifestyle,
                )
                updatedUser.copy(dailyNutrientsGoal = recommendedNutrients)
            } else {
                updatedUser
            }

            val result = updateUserUseCase(
                updateUser = UpdateUser(
                    age = userToSave.age,
                    heightCm = userToSave.heightCm,
                    weightGrams = userToSave.weightGrams,
                    weightGoal = userToSave.weightGoal,
                    lifestyle = userToSave.lifestyle,
                    gender = userToSave.gender,
                    dailyNutrientsGoal = userToSave.dailyNutrientsGoal,
                )
            )

            if (result.weightException != null) {
                handleException(exception = result.weightException, null)
            }

            if (result.ageException != null) {
                handleException(exception = result.ageException, null)
            }

            if (result.heightException != null) {
                handleException(exception = result.heightException, null)
            }

            when (val response = result.response) {
                is AppResponse.Success -> {
                    sendUiEvent(UserInfoUiEvent.UserInfoSaved)
                }

                is AppResponse.Failed -> {
                    handleException(response.appException, response.message)
                }

                null -> Unit
            }

            state = state.copy(isLoading = false)
        }
    }

    private fun changeHeightUnit(heightUnit: HeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeHeightUnit(heightUnit)
        }
    }

    private fun getHeightUnit() {
        viewModelScope.launch {
            appSettingsManager.heightUnit.collect { unit ->
                state = state.copy(heightUnit = unit)
            }
        }
    }

    private fun changeWeightUnit(weightUnit: WeightUnit) {
        viewModelScope.launch {
            appSettingsManager.changeWeightUnit(weightUnit)
        }
    }

    private fun getWeightUnit() {
        viewModelScope.launch {
            appSettingsManager.weightUnit.collect { unit ->
                state = state.copy(weightUnit = unit)
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collect { user ->
                this@UserInfoViewModel.originalUser = user
                state = state.copy(user = user)
            }
        }
    }

    private fun sendUiEvent(event: UserInfoUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun handleException(exception: AppException, message: String?) {
        when (exception) {
            is UserInfoException.InvalidHeightException -> {
                val errorMessage = application.getString(R.string.error_invalid_height)
                sendUiEvent(UserInfoUiEvent.ShowSnackbar(errorMessage))
            }

            is UserInfoException.InvalidWeightException -> {
                val errorMessage = application.getString(R.string.error_invalid_weight)
                sendUiEvent(UserInfoUiEvent.ShowSnackbar(errorMessage))
            }

            is UserInfoException.InvalidAgeException -> {
                val errorMessage = application.getString(R.string.error_invalid_age)
                sendUiEvent(UserInfoUiEvent.ShowSnackbar(errorMessage))
            }

            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                sendUiEvent(UserInfoUiEvent.ShowSnackbar(errorMessage))
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(UserInfoUiEvent.ShowSnackbar(errorMessage))
            }
        }
    }

}