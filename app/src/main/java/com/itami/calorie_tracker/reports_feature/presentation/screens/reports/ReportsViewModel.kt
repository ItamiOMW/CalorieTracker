package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiEvent = Channel<ReportsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(ReportsState())
        private set

    fun onEvent(event: ReportsEvent) {
        when (event) {
            else -> {

            }
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            _uiEvent.send(ReportsUiEvent.ShowSnackbar(message))
        }
    }

}