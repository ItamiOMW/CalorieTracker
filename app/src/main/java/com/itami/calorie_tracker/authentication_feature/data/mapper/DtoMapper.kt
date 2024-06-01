package com.itami.calorie_tracker.authentication_feature.data.mapper

import com.itami.calorie_tracker.authentication_feature.data.remote.request.EmailRegisterRequest
import com.itami.calorie_tracker.authentication_feature.data.remote.request.GoogleRegisterRequest
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserEmail
import com.itami.calorie_tracker.authentication_feature.domain.model.CreateUserGoogle

fun CreateUserGoogle.toRegisterGoogleRequest() = GoogleRegisterRequest(
    googleIdToken = this.googleIdToken,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyNutrientsGoal.caloriesGoal,
    dailyProteins = this.dailyNutrientsGoal.proteinsGoal,
    dailyFats = this.dailyNutrientsGoal.fatsGoal,
    dailyCarbs = this.dailyNutrientsGoal.carbsGoal,
    waterMl = this.dailyNutrientsGoal.waterMlGoal
)

fun CreateUserEmail.toEmailRegisterRequest() = EmailRegisterRequest(
    email = this.email,
    password = this.password,
    name = this.name,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyNutrientsGoal.caloriesGoal,
    dailyProteins = this.dailyNutrientsGoal.proteinsGoal,
    dailyFats = this.dailyNutrientsGoal.fatsGoal,
    dailyCarbs = this.dailyNutrientsGoal.carbsGoal,
    waterMl = this.dailyNutrientsGoal.waterMlGoal
)