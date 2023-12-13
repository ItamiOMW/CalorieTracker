package com.itami.calorie_tracker.authentication_feature.domain.model

import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal

data class CreateUserGoogle(
    val googleIdToken: String,
    val age: Int,
    val heightCm: Int,
    val weightGrams: Int,
    val lifestyle: Lifestyle,
    val gender: Gender,
    val weightGoal: WeightGoal,
    val dailyNutrientsGoal: DailyNutrientsGoal,
)
