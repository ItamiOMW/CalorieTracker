package com.itami.calorie_tracker.recipes_feature.domain.model

import com.itami.calorie_tracker.core.utils.Constants

data class Recipe(
    val id: Int = Constants.UNKNOWN_ID,
    val name: String,
    val recipeText: String,
    val caloriesPerServing: Int,
    val proteinsPerServing: Int,
    val fatsPerServing: Int,
    val carbsPerServing: Int,
    val timeMinutes: Int,
    val imageUrl: String,
)