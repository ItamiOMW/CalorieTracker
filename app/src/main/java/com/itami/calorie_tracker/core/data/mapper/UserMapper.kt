package com.itami.calorie_tracker.core.data.mapper

import com.itami.calorie_tracker.core.data.remote.request.UpdateUserRequest
import com.itami.calorie_tracker.core.data.remote.response.UserResponse
import com.itami.calorie_tracker.core.data.repository.user_manager.DataStoreUser
import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.domain.model.UpdateUser
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
    dailyNutrientsGoal = DailyNutrientsGoal(
        caloriesGoal = this.dailyCalories,
        proteinsGoal = this.dailyProteins,
        fatsGoal = this.dailyFats,
        carbsGoal = this.dailyCarbs,
        waterMlGoal = this.waterMl
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
    dailyCalories = this.dailyNutrientsGoal.caloriesGoal,
    dailyProteins = this.dailyNutrientsGoal.proteinsGoal,
    dailyFats = this.dailyNutrientsGoal.fatsGoal,
    dailyCarbs = this.dailyNutrientsGoal.carbsGoal,
    waterMl = this.dailyNutrientsGoal.waterMlGoal
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
    dailyNutrientsGoal = DailyNutrientsGoal(
        caloriesGoal = this.dailyCalories,
        proteinsGoal = this.dailyProteins,
        fatsGoal = this.dailyFats,
        carbsGoal = this.dailyCarbs,
        waterMlGoal = this.waterMl
    )
)

fun UpdateUser.toUpdateUserRequest() = UpdateUserRequest(
    name = this.name,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    caloriesGoal = this.dailyNutrientsGoal?.caloriesGoal,
    proteinsGoal = this.dailyNutrientsGoal?.proteinsGoal,
    fatsGoal = this.dailyNutrientsGoal?.fatsGoal,
    carbsGoal = this.dailyNutrientsGoal?.carbsGoal,
    waterMlGoal = this.dailyNutrientsGoal?.waterMlGoal,
)