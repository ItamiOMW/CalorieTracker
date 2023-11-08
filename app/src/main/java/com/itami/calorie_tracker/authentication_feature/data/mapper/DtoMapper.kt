package com.itami.calorie_tracker.authentication_feature.data.mapper

import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle


fun CreateUserGoogle.toRegisterGoogleRequest() = GoogleRegisterRequest(
    googleIdToken = this.googleIdToken,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyNutrients.calories,
    dailyProteins = this.dailyNutrients.proteins,
    dailyFats = this.dailyNutrients.fats,
    dailyCarbs = this.dailyNutrients.carbs,
    waterMl = this.dailyNutrients.waterMl
)