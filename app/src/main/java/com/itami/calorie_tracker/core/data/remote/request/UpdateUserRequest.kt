package com.itami.calorie_tracker.core.data.remote.request

import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val age: Int? = null,
    val heightCm: Int? = null,
    val weightGrams: Int? = null,
    val lifestyle: Lifestyle? = null,
    val gender: Gender? = null,
    val weightGoal: WeightGoal? = null,
    val caloriesGoal: Int? = null,
    val proteinsGoal: Int? = null,
    val fatsGoal: Int? = null,
    val carbsGoal: Int? = null,
    val waterMlGoal: Int? = null,
)
