package com.itami.calorie_tracker.core.domain.repository

import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import kotlinx.coroutines.flow.Flow

interface AppSettingsManager {

    val isDarkMode: Flow<Boolean>

    suspend fun changeDarkModeState(enabled: Boolean)

    val showOnboarding: Flow<Boolean>

    suspend fun changeShowOnboardingState(show: Boolean)

    val weightUnit: Flow<WeightUnit>

    suspend fun getWeightUnit(): WeightUnit

    suspend fun changeWeightUnit(weightUnit: WeightUnit)

    val heightUnit: Flow<HeightUnit>

    suspend fun getHeightUnit(): HeightUnit

    suspend fun changeHeightUnit(heightUnit: HeightUnit)
}