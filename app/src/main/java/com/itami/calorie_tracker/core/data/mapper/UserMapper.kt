package com.itami.calorie_tracker.core.data.mapper

import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.core.data.repository.user_manager.DataStoreUser
import com.itami.calorie_tracker.core.domain.model.DailyNutrients
import com.itami.calorie_tracker.core.domain.model.User


fun DataStoreUser.toUser() = User(
    id = this.id,
    name = this.name,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = this.createdAt,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyNutrients = DailyNutrients(
        calories = this.dailyCalories,
        proteins = this.dailyProteins,
        fats = this.dailyFats,
        carbs = this.dailyCarbs,
        waterMl = this.waterMl
    )
)

fun User.toDataStoreUser() = DataStoreUser(
    id = this.id,
    name = this.name,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = this.createdAt,
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

fun UserResponse.toUser() = User(
    id = this.id,
    name = this.name,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = this.createdAt,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyNutrients = DailyNutrients(
        calories = this.dailyCalories,
        proteins = this.dailyProteins,
        fats = this.dailyFats,
        carbs = this.dailyCarbs,
        waterMl = this.waterMl
    )
)