package com.itami.calorie_tracker.core.di

import android.content.Context
import androidx.room.Room
import com.itami.calorie_tracker.core.data.local.CalorieTrackerDatabase
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedFoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedWaterDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.FoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): CalorieTrackerDatabase {
        return Room.databaseBuilder(
            context,
            CalorieTrackerDatabase::class.java,
            CalorieTrackerDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(
        db: CalorieTrackerDatabase
    ): FoodDao = db.foodDao()

    @Provides
    @Singleton
    fun provideMealDao(
        db: CalorieTrackerDatabase
    ): MealDao = db.mealDao()

    @Provides
    @Singleton
    fun provideConsumedFoodDao(
        db: CalorieTrackerDatabase
    ): ConsumedFoodDao = db.consumedFoodDao()

    @Provides
    @Singleton
    fun provideConsumedWaterDao(
        db: CalorieTrackerDatabase
    ): ConsumedWaterDao = db.consumedWaterDao()

}