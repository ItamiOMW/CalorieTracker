package com.itami.calorie_tracker.core.domain.repository

import com.itami.calorie_tracker.core.domain.model.DailyNutrients
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import kotlinx.coroutines.flow.Flow

interface UserManager {

    val user: Flow<User>

    suspend fun getUser(): User

    suspend fun setUser(user: User?)

    suspend fun setGoal(goal: WeightGoal)

    suspend fun setGender(gender: Gender)

    suspend fun setLifestyle(lifestyle: Lifestyle)

    suspend fun setHeight(heightCm: Int)

    suspend fun setWeight(weightGrams: Int)

    suspend fun setAge(age: Int)

    suspend fun setDailyNutrients(dailyNutrients: DailyNutrients)

}