package com.itami.calorie_tracker.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreAppSettingsManager @Inject constructor(
    @ApplicationContext context: Context,
) : AppSettingsManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_DATASTORE_NAME
    )

    private val dataStore: DataStore<Preferences> = context.dataStore


    override val theme: Flow<Theme> = dataStore.data.map { preferences ->
        preferences[THEME_KEY]?.let { Theme.valueOf(it) } ?: Theme.SYSTEM_THEME
    }

    override suspend fun changeTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }

    override val showOnboarding: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[SHOW_ONBOARDING_KEY] ?: true
    }

    override suspend fun getShowOnboardingState(): Boolean {
        return dataStore.data.first()[SHOW_ONBOARDING_KEY] ?: true
    }

    override suspend fun changeShowOnboardingState(show: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_ONBOARDING_KEY] = show
        }
    }

    override val weightUnit: Flow<WeightUnit> = dataStore.data.map { preferences ->
        preferences[WEIGHT_UNIT_KEY]?.let { WeightUnit.valueOf(it) } ?: WeightUnit.KILOGRAM
    }

    override suspend fun getWeightUnit(): WeightUnit {
        return dataStore.data.first()[WEIGHT_UNIT_KEY]?.let {
            WeightUnit.valueOf(it)
        } ?: WeightUnit.KILOGRAM
    }

    override suspend fun changeWeightUnit(weightUnit: WeightUnit) {
        dataStore.edit { preferences ->
            preferences[WEIGHT_UNIT_KEY] = weightUnit.name
        }
    }

    override val heightUnit: Flow<HeightUnit> = dataStore.data.map { preferences ->
        preferences[HEIGHT_UNIT_KEY]?.let { HeightUnit.valueOf(it) } ?: HeightUnit.METER
    }

    override suspend fun getHeightUnit(): HeightUnit {
        return dataStore.data.first()[HEIGHT_UNIT_KEY]?.let {
            HeightUnit.valueOf(it)
        } ?: HeightUnit.METER
    }

    override suspend fun changeHeightUnit(heightUnit: HeightUnit) {
        dataStore.edit { preferences ->
            preferences[HEIGHT_UNIT_KEY] = heightUnit.name
        }
    }

    override val waterTrackerEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[WATER_INTAKE_ENABLED] ?: true
    }

    override suspend fun changeWaterTrackerEnabledState(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[WATER_INTAKE_ENABLED] = enabled
        }
    }

    override val waterServingSize: Flow<Int> = dataStore.data.map { preferences ->
        preferences[WATER_SERVING_SIZE_KEY] ?: Constants.DEFAULT_WATER_SERVING_ML
    }

    override suspend fun changeWaterServingSize(sizeMl: Int) {
        dataStore.edit { preferences ->
            preferences[WATER_SERVING_SIZE_KEY] = sizeMl
        }
    }

    companion object {

        private const val PREFERENCES_DATASTORE_NAME = "app_settings_preferences"

        private val THEME_KEY = stringPreferencesKey("theme")

        private val SHOW_ONBOARDING_KEY = booleanPreferencesKey("show_onboarding")

        private val WEIGHT_UNIT_KEY = stringPreferencesKey("weight_unit")

        private val HEIGHT_UNIT_KEY = stringPreferencesKey("height_unit")

        private val WATER_INTAKE_ENABLED = booleanPreferencesKey("water_tracker_enabled")

        private val WATER_SERVING_SIZE_KEY = intPreferencesKey("water_serving_size")

    }

}