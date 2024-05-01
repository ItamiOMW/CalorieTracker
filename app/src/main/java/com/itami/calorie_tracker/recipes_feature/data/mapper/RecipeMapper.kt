package com.itami.calorie_tracker.recipes_feature.data.mapper

import com.itami.calorie_tracker.recipes_feature.data.remote.response.RecipeResponse
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe

fun RecipeResponse.toRecipe() = Recipe(
    id = this.id,
    name = this.name,
    recipeText = this.recipeText,
    caloriesPerServing = this.caloriePerServing,
    proteinsPerServing = this.proteinsPerServing,
    fatsPerServing = this.fatsPerServing,
    carbsPerServing = this.carbsPerServing,
    timeMinutes = this.timeMinutes,
    imageUrl = this.imageUrl
)