package com.itami.calorie_tracker.settings_feature.presentation.screens.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor() : ViewModel() {

    private val _uiEvent = Channel<LanguageUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LanguageState())
        private set

    init {
        state = state.copy(selectedLocale = Locale.getDefault())
    }

    fun onAction(action: LanguageAction) {
        when(action) {
            is LanguageAction.NavigateBackClick -> {
                sendUiEvent(LanguageUiEvent.NavigateBack)
            }

            is LanguageAction.LanguageClick -> {
                changeLanguage(action.locale)
            }
        }
    }

    private fun sendUiEvent(event: LanguageUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun changeLanguage(locale: Locale) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        )
        state = state.copy(selectedLocale = locale)
    }

}