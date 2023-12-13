package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.NetworkException
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

    private var searchQueryJob: Job? = null

    fun onEvent(event: SearchFoodEvent) {
        when (event) {
            is SearchFoodEvent.SearchQueryChange -> {
                state = state.copy(searchQuery = event.newValue)
                refreshFoodPaginator(delayMillis = 750)
            }

            is SearchFoodEvent.ClearSearchQuery -> {
                state = state.copy(searchQuery = "")
                refreshFoodPaginator(delayMillis = 1000)
            }

            is SearchFoodEvent.SetSelectedFood -> {
                state = state.copy(
                    selectedFood = event.food?.let { ConsumedFood(food = it) }
                )
            }

            is SearchFoodEvent.LoadNextPage -> {
                loadNextPage()
            }

            is SearchFoodEvent.Refresh -> {
                refreshFoodPaginator(delayMillis = 0)
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

    private fun handleException(exception: Exception, message: String?) {
        when (exception) {
            is NetworkException -> {
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