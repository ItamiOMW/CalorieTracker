package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.exceptions.AppException
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.recipes_feature.domain.use_case.GetRecipeByIdUseCase
import com.itami.calorie_tracker.recipes_feature.presentation.RecipesGraphScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
) : ViewModel() {

    var state by mutableStateOf(RecipeDetailState())
        private set

    private var recipeId: Int = Constants.UNKNOWN_ID

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>(RecipesGraphScreen.RECIPE_ID_ARG)?.let { recipeId ->
                this@RecipeDetailViewModel.recipeId = recipeId
                getRecipe(recipeId)
            } ?: throw Exception("Recipe Id argument was not passed.")
        }
    }

    fun onAction(action: RecipeDetailAction) {
        when (action) {
            is RecipeDetailAction.Retry -> {
                state = state.copy(errorMessage = null)
                getRecipe(recipeId)
            }
        }
    }

    private fun getRecipe(recipeId: Int) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            when (val response = getRecipeByIdUseCase(recipeId)) {
                is AppResponse.Success -> {
                    state = state.copy(recipe = response.data)
                }

                is AppResponse.Failed -> {
                    handleException(
                        appException = response.appException,
                        message = response.message
                    )
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun handleException(appException: AppException, message: String?) {
        when (appException) {
            is AppException.NetworkException -> {
                val errorMessage = application.getString(R.string.error_network)
                state = state.copy(errorMessage = errorMessage)
            }

            else -> {
                val errorMessage = message ?: application.getString(R.string.error_unknown)
                state = state.copy(errorMessage = errorMessage)
            }
        }
    }

}