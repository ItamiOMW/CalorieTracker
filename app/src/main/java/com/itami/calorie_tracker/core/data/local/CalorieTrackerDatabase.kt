package com.itami.calorie_tracker.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itami.calorie_tracker.diary_feature.data.local.converter.ZonedDateTimeConverter
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedFoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.ConsumedWaterDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.FoodDao
import com.itami.calorie_tracker.diary_feature.data.local.dao.MealDao
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedFoodEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedWaterEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.FoodEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.MealEntity

@Database(
    entities = [
        FoodEntity::class,
        MealEntity::class,
        ConsumedFoodEntity::class,
        ConsumedWaterEntity::class
    ],
    exportSchema = false,
    version = 1,
)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class CalorieTrackerDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    abstract fun foodDao(): FoodDao

    abstract fun consumedFoodDao(): ConsumedFoodDao

    abstract fun consumedWaterDao(): ConsumedWaterDao

    companion object {

        const val DB_NAME = "calorie_tracker.db"

    }

}