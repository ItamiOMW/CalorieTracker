package com.itami.calorie_tracker.authentication_feature.presentation.screens.recommended_nutrients

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle
import com.itami.calorie_tracker.authentication_feature.domain.use_case.RegisterGoogleUseCase
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.use_case.CalculateNutrientsUseCase
import com.itami.calorie_tracker.core.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedNutrientsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val calculateNutrientsUseCase: CalculateNutrientsUseCase,
    private val registerGoogleUseCase: RegisterGoogleUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<RecommendedNutrientUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(RecommendedNutrientsState())
        private set

    private lateinit var user: User

    init {
        getUserAndCalculateNutrients()
    }

    fun onEvent(event: RecommendedNutrientEvent) {
        when (event) {
            is RecommendedNutrientEvent.ShowGoogleOneTap -> {
                state = state.copy(showGoogleOneTap = event.show)
            }

            is RecommendedNutrientEvent.SignUpWithGoogle -> {
                signUpWithGoogle(
                    idToken = event.idToken,
                    user = user,
                )
            }
        }
    }

    private fun getUserAndCalculateNutrients() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val user = userManager.getUser()
            val nutrients = calculateNutrientsUseCase(
                age = user.age,
                heightCm = user.heightCm,
                weightGrams = user.weightGrams,
                weightGoal = user.weightGoal,
                gender = user.gender,
                lifestyle = user.lifestyle,
            )
            this@RecommendedNutrientsViewModel.user = user.copy(dailyNutrientsGoal = nutrients)
            userManager.setDailyNutrients(nutrients)
            state = state.copy(dailyNutrientsGoal = nutrients, isLoading = false)
        }
    }

    private fun sendUiEvent(uiEvent: RecommendedNutrientUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun signUpWithGoogle(
        idToken: String,
        user: User,
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val createUser = CreateUserGoogle(
                googleIdToken = idToken,
                age = user.age,
                heightCm = user.heightCm,
                weightGrams = user.weightGrams,
                lifestyle = user.lifestyle,
                gender = user.gender,
                weightGoal = user.weightGoal,
                dailyNutrientsGoal = user.dailyNutrientsGoal
            )
            when (val response = registerGoogleUseCase(createUser)) {
                is AppResponse.Success -> {
                    sendUiEvent(RecommendedNutrientUiEvent.SignUpSuccessful)
                }

                is AppResponse.Failed -> {
                    handleException(appException = response.appException, message = response.message)
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                sendUiEvent(
                    RecommendedNutrientUiEvent.ShowSnackbar(
                        application.getString(R.string.error_network)
                    )
                )
            }

            is AppException.UnauthorizedException -> {
                sendUiEvent(
                    RecommendedNutrientUiEvent.ShowSnackbar(
                        message = message ?: application.getString(R.string.error_google_unauthorized)
                    )
                )
            }

            else -> {
                sendUiEvent(
                    RecommendedNutrientUiEvent.ShowSnackbar(
                        message = message ?: application.getString(R.string.error_unknown)
                    )
                )
            }
        }
    }

}