package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.paginator.DefaultPaginator
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.recipes_feature.domain.use_case.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val application: Application,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val userManager: UserManager,
) : ViewModel() {

    private val _uiEvent = Channel<RecipesUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(RecipesState())
        private set

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = { state = state.copy(isLoadingNextRecipes = it) },
        onRequest = { nextKey ->
            getRecipesUseCase(
                query = state.searchQuery,
                page = nextKey,
                timeFilters = state.timeFilters,
                caloriesFilters = state.caloriesFilters
            )
        },
        getNextKey = { state.page + 1 },
        onSuccess = { items, newKey ->
            state = state.copy(
                recipes = state.recipes + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        },
        onError = {
            handleException(appException = it)
        },
    )

    private var loadRecipesJob: Job? = null

    init {
        getUser()
    }

    fun onAction(action: RecipesAction) {
        when (action) {
            is RecipesAction.Refresh -> {
                refreshRecipes()
            }

            is RecipesAction.SearchQueryChange -> {
                state = state.copy(searchQuery = action.newValue)
                loadRecipesJob?.cancel()
                loadRecipesJob = viewModelScope.launch {
                    delay(1000L)
                    refreshRecipes()
                }
            }

            is RecipesAction.ShowFilterOverlay -> {
                state = state.copy(showFilterOverlay = action.show)
            }

            is RecipesAction.LoadNextRecipes -> {
                loadNextRecipes()
            }

            is RecipesAction.UpdateFilters -> {
                state = state.copy(
                    timeFilters = action.timeFilters,
                    caloriesFilters = action.caloriesFilters
                )
                loadRecipesJob?.cancel()
                loadRecipesJob = viewModelScope.launch {
                    refreshRecipes()
                }
            }
        }
    }

    private fun loadNextRecipes() {
        viewModelScope.launch {
            paginator.loadNextPage()
        }
    }

    private fun refreshRecipes() {
        viewModelScope.launch {
            state = state.copy(
                recipes = emptyList(),
                endReached = false,
                page = 1,
                messageError = null
            )
            paginator.reset()
            paginator.loadNextPage()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            userManager.user.collect { user ->
                state = state.copy(user = user)
            }
        }
    }

    private fun sendUiEvent(uiEvent: RecipesUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(appException: AppException, message: String? = null) {
        when (appException) {
            is AppException.NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                state = state.copy(messageError = messageError)
                sendUiEvent(RecipesUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                state = state.copy(messageError = messageError)
                sendUiEvent(RecipesUiEvent.ShowSnackbar(messageError))
            }
        }
    }

}