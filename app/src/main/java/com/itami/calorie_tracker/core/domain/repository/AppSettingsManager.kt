package com.itami.calorie_tracker.core.domain.repository

import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import kotlinx.coroutines.flow.Flow

interface AppSettingsManager {

    val theme: Flow<Theme>

    suspend fun changeTheme(theme: Theme)


    val showOnboarding: Flow<Boolean>

    suspend fun getShowOnboardingState(): Boolean

    suspend fun changeShowOnboardingState(show: Boolean)


    val weightUnit: Flow<WeightUnit>

    suspend fun getWeightUnit(): WeightUnit

    suspend fun changeWeightUnit(weightUnit: WeightUnit)


    val heightUnit: Flow<HeightUnit>

    suspend fun getHeightUnit(): HeightUnit

    suspend fun changeHeightUnit(heightUnit: HeightUnit)


    val waterServingSize: Flow<Int>

    suspend fun changeWaterServingSize(sizeMl: Int)
}