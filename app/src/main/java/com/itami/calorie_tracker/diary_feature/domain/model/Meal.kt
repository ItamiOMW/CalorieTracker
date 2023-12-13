package com.itami.calorie_tracker.diary_feature.domain.model

import kotlin.math.roundToInt

data class Meal(
    val id: Int,
    val name: String,
    val consumedFoods: List<ConsumedFood>,
    val createdAt: String,
) {
    fun getProteins(): Int {
        return consumedFoods.sumOf { (it.grams / 100f * it.food.proteinsIn100Grams).roundToInt() }
    }

    fun getFats(): Int {
        return consumedFoods.sumOf { (it.grams / 100f * it.food.fatsIn100Grams).roundToInt() }
    }

    fun getCarbs(): Int {
        return consumedFoods.sumOf { (it.grams / 100f * it.food.carbsIn100Grams).roundToInt() }
    }

    fun getCalories(): Int {
        return consumedFoods.sumOf { (it.grams / 100f * it.food.caloriesIn100Grams).roundToInt() }
    }
}
