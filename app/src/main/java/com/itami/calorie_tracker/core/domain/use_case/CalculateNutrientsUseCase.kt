package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import kotlin.math.roundToInt

class CalculateNutrientsUseCase {

    operator fun invoke(
        age: Int,
        heightCm: Int,
        weightGrams: Int,
        gender: Gender,
        weightGoal: WeightGoal,
        lifestyle: Lifestyle,
        carbsRatio: Float = 0.4f,
        proteinsRatio: Float = 0.3f,
        fatsRatio: Float = 0.3f,
    ): DailyNutrientsGoal {
        val weightKgs = WeightUnit.KILOGRAM.gramsToKilograms(grams = weightGrams)
        //Factor in Physical Activity Level (PAL)
        val pal = when (lifestyle) {
            Lifestyle.SEDENTARY -> {
                1.2
            }

            Lifestyle.LOW_ACTIVE -> {
                1.375
            }

            Lifestyle.ACTIVE -> {
                1.55
            }

            Lifestyle.VERY_ACTIVE -> {
                1.725
            }
        }
        //Adjust calories with specific goal
        val calorieAdjustment = when (weightGoal) {
            WeightGoal.LOSE_WEIGHT -> -500
            WeightGoal.KEEP_WEIGHT -> 0
            WeightGoal.GAIN_WEIGHT -> 500
        }
        //Basal Metabolic Rate (BMR)
        val bmr = when (gender) {
            Gender.MALE -> {
                88.362 + (13.397 * weightKgs) + (4.799 * heightCm) - (5.677 * age)
            }

            Gender.FEMALE -> {
                447.593 + (9.247 * weightKgs) + (3.098 * heightCm) - (4.330 * age)
            }
        }
        //Total Daily Energy Expenditure (TDEE)
        val tdee = (bmr * pal + calorieAdjustment).roundToInt()
        val proteins = (tdee * proteinsRatio / 4).roundToInt()
        val fats = (tdee * fatsRatio / 9).roundToInt()
        val carbs = (tdee * carbsRatio / 4).roundToInt()
        val waterMl = (weightKgs * 30).roundToInt()
        return DailyNutrientsGoal(
            caloriesGoal = tdee,
            proteinsGoal = proteins,
            fatsGoal = fats,
            carbsGoal = carbs,
            waterMlGoal = waterMl
        )
    }
}