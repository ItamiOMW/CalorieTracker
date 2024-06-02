package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.domain.paginator.DefaultPaginator
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.use_case.SearchFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val searchFoodUseCase: SearchFoodUseCase,
    private val application: Application,
) : ViewModel() {

    private val _uiEvent = Channel<SearchFoodUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SearchFoodState())
        private set

    private var searchQueryJob: Job? = null

    private val foodPaginator = DefaultPaginator(
        initialKey = state.foodsPage,
        onLoadUpdated = { isLoading ->
            state = state.copy(isLoadingNextPage = isLoading)
        },
        onRequest = { nextPage ->
            searchFoodUseCase(query = state.searchQuery, page = nextPage)
        },
        getNextKey = {
            state.foodsPage + 1
        },
        onError = { exception ->
            handleException(exception, null)
        },
        onSuccess = { foods, newPage ->
            state = state.copy(
                foods = state.foods + foods,
                foodsPage = newPage,
                endReached = foods.isEmpty()
            )
        }
    )

    fun onAction(action: SearchFoodAction) {
        when (action) {
            is SearchFoodAction.SearchQueryChange -> {
                state = state.copy(searchQuery = action.newValue)
                refreshFoodPaginator(delayMillis = 750)
            }

            is SearchFoodAction.ClearSearchQueryClick -> {
                state = state.copy(searchQuery = "")
                refreshFoodPaginator(delayMillis = 1000)
            }

            is SearchFoodAction.FoodClick -> {
                state = state.copy(selectedFood = ConsumedFood(food = action.food))
            }

            is SearchFoodAction.LoadNextPage -> {
                loadNextPage()
            }

            is SearchFoodAction.Refresh -> {
                refreshFoodPaginator(delayMillis = 0)
            }

            is SearchFoodAction.AddConsumedFood -> {
                sendUiEvent(SearchFoodUiEvent.NavigateBackWithFood(action.consumedFood))
                state = state.copy(selectedFood = null)
            }

            is SearchFoodAction.NavigateBackClick -> {
               sendUiEvent(SearchFoodUiEvent.NavigateBack)
            }

            is SearchFoodAction.DismissConsumedFoodDialog -> {
                state = state.copy(selectedFood = null)
            }
        }
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            foodPaginator.loadNextPage()
        }
    }

    private fun refreshFoodPaginator(delayMillis: Long = 750) {
        searchQueryJob?.cancel()
        searchQueryJob = viewModelScope.launch {
            delay(delayMillis)
            state = state.copy(foodsPage = 1, foods = emptyList(), endReached = false)
            foodPaginator.reset()
            foodPaginator.loadNextPage()
        }
    }

    private fun sendUiEvent(uiEvent: SearchFoodUiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                val messageError = application.getString(R.string.error_network)
                sendUiEvent(SearchFoodUiEvent.ShowSnackbar(messageError))
            }

            else -> {
                val messageError = message ?: application.getString(R.string.error_unknown)
                sendUiEvent(SearchFoodUiEvent.ShowSnackbar(messageError))
            }
        }
    }

}