package com.itami.calorie_tracker.authentication_feature.data.remote.request

import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class GoogleRegisterRequest(
    val googleIdToken: String,
    val age: Int,
    val heightCm: Int,
    val weightGrams: Int,
    val gender: Gender,
    val weightGoal: WeightGoal,
    val lifestyle: Lifestyle,
    val dailyCalories: Int,
    val dailyProteins: Int,
    val dailyFats: Int,
    val dailyCarbs: Int,
    val waterMl: Int
)
