package com.itami.calorie_tracker.recipes_feature.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Int,
    val name: String,
    val recipeText: String,
    val caloriePerServing: Int,
    val proteinsPerServing: Int,
    val fatsPerServing: Int,
    val carbsPerServing: Int,
    val timeMinutes: Int,
    val imageUrl: String,
)