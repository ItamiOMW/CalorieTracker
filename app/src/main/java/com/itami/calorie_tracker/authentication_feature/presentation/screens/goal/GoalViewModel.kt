package com.itami.calorie_tracker.authentication_feature.presentation.screens.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<GoalUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(GoalState())
        private set

    init {
        getGoalState()
    }

    fun onAction(action: GoalAction) {
        when (action) {
            is GoalAction.SaveGoal -> {
                saveGoal(state.selectedGoal)
            }

            is GoalAction.SelectGoal -> {
                state = state.copy(selectedGoal = action.weightGoal)
            }
        }
    }

    private fun getGoalState() {
        viewModelScope.launch {
            val user = userManager.getUser()
            state = state.copy(selectedGoal = user.weightGoal)
        }
    }

    private fun saveGoal(goal: WeightGoal) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            userManager.setGoal(goal = goal)
            state = state.copy(isLoading = false)
            _uiEvent.send(GoalUiEvent.GoalSaved)
        }
    }
}