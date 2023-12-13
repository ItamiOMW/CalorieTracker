package com.itami.calorie_tracker.diary_feature.presentation.mapper


import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedWater
import com.itami.calorie_tracker.diary_feature.domain.model.Meal
import com.itami.calorie_tracker.diary_feature.presentation.model.ConsumedWaterUi
import com.itami.calorie_tracker.diary_feature.presentation.model.MealUi

fun Meal.toMealUi() = MealUi(
    id = this.id,
    name = this.name,
    consumedFoods = this.consumedFoods,
    createdAt = DateTimeUtil.stringToDateTime(this.createdAt),
    proteins = this.getProteins(),
    fats = this.getFats(),
    carbs = this.getCarbs(),
    calories = this.getCalories()
)

fun ConsumedWater.toConsumedWaterUi() = ConsumedWaterUi(
    id = this.id,
    timestamp = DateTimeUtil.stringToDateTime(this.timestamp),
    waterMl = this.waterMl,
)