package com.itami.calorie_tracker.core.data.repository.user_manager

import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.utils.Constants
import kotlinx.serialization.Serializable

@Serializable
data class DataStoreUser(
    val id: Int = Constants.UNKNOWN_ID,
    val email: String = Constants.EMPTY_STRING,
    val name: String = Constants.EMPTY_STRING,
    val profilePictureUrl: String? = null,
    val createdAt: Long = 0L,
    val age: Int = Constants.DEFAULT_AGE,
    val heightCm: Int = Constants.DEFAULT_HEIGHT_CM,
    val weightGrams: Int = Constants.DEFAULT_WEIGHT_GRAMS,
    val gender: Gender = Gender.MALE,
    val weightGoal: WeightGoal = WeightGoal.GAIN_WEIGHT,
    val lifestyle: Lifestyle = Lifestyle.LOW_ACTIVE,
    val dailyCalories: Int = Constants.DEFAULT_DAILY_CALORIES,
    val dailyProteins: Int = Constants.DEFAULT_DAILY_PROTEINS,
    val dailyFats: Int = Constants.DEFAULT_DAILY_FATS,
    val dailyCarbs: Int = Constants.DEFAULT_DAILY_CARBS,
    val waterMl: Int = Constants.DEFAULT_WATER_ML,
)