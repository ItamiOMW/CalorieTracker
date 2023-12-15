package com.itami.calorie_tracker.core.domain.model

import com.itami.calorie_tracker.core.utils.Constants
import com.itami.calorie_tracker.core.utils.DateTimeUtil

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val profilePictureUrl: String?,
    val createdAt: String,
    val age: Int,
    val heightCm: Int,
    val weightGrams: Int,
    val gender: Gender,
    val weightGoal: WeightGoal,
    val lifestyle: Lifestyle,
    val dailyNutrientsGoal: DailyNutrientsGoal,
) {

    companion object {

        val DEFAULT = User(
            id = Constants.UNKNOWN_ID,
            email = "useremail@gmail.com",
            name = "Username",
            createdAt = DateTimeUtil.getCurrentDateTimeString(),
            profilePictureUrl = null,
            age = Constants.DEFAULT_AGE,
            heightCm = Constants.DEFAULT_HEIGHT_CM,
            weightGrams = Constants.DEFAULT_WEIGHT_GRAMS,
            gender = Gender.MALE,
            weightGoal = WeightGoal.KEEP_WEIGHT,
            lifestyle = Lifestyle.LOW_ACTIVE,
            dailyNutrientsGoal = DailyNutrientsGoal(
                caloriesGoal = Constants.DEFAULT_DAILY_CALORIES,
                proteinsGoal = Constants.DEFAULT_DAILY_PROTEINS,
                fatsGoal = Constants.DEFAULT_DAILY_FATS,
                carbsGoal = Constants.DEFAULT_DAILY_CARBS,
                waterMlGoal = Constants.DEFAULT_DAILY_WATER_ML
            )
        )

    }
}
