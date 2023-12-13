package com.itami.calorie_tracker.core.data.repository.user_manager

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.itami.calorie_tracker.core.data.mapper.toDataStoreUser
import com.itami.calorie_tracker.core.data.mapper.toUser
import com.itami.calorie_tracker.core.domain.model.DailyNutrientsGoal
import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreUserManager @Inject constructor(
    @ApplicationContext context: Context,
) : UserManager {

    private val dataStore = DataStoreFactory.create(DataStoreUserSerializer, produceFile = {
        context.dataStoreFile(USER_DATASTORE_NAME)
    })

    override val user: Flow<User>
        get() = dataStore.data.map { it.toUser() }

    override suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            dataStore.data.first().toUser()
        }
    }

    override suspend fun setUser(user: User?) {
        dataStore.updateData {
            user?.toDataStoreUser() ?: DataStoreUser()
        }
    }

    override suspend fun setGoal(goal: WeightGoal) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(weightGoal = goal)
        }
    }

    override suspend fun setGender(gender: Gender) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(gender = gender)
        }
    }

    override suspend fun setLifestyle(lifestyle: Lifestyle) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(lifestyle = lifestyle)
        }
    }

    override suspend fun setHeight(heightCm: Int) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(heightCm = heightCm)
        }
    }

    override suspend fun setWeight(weightGrams: Int) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(weightGrams = weightGrams)
        }
    }

    override suspend fun setAge(age: Int) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(age = age)
        }
    }

    override suspend fun setDailyNutrients(dailyNutrientsGoal: DailyNutrientsGoal) {
        dataStore.updateData { datastoreUser ->
            datastoreUser.copy(
                dailyCalories = dailyNutrientsGoal.caloriesGoal,
                dailyProteins = dailyNutrientsGoal.proteinsGoal,
                dailyFats = dailyNutrientsGoal.fatsGoal,
                dailyCarbs = dailyNutrientsGoal.carbsGoal
            )
        }
    }

    companion object {

        private const val USER_DATASTORE_NAME = "user_datastore"

    }

}