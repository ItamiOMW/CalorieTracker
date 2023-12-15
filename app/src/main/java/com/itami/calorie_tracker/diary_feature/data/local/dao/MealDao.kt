package com.itami.calorie_tracker.diary_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.itami.calorie_tracker.diary_feature.data.local.entity.MealEntity
import com.itami.calorie_tracker.diary_feature.data.local.entity.MealWithConsumedFoods
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Transaction
    @Query("SELECT * FROM meals WHERE SUBSTR(utcCreatedAt, 1, 10) = SUBSTR(:utcDate, 1, 10)")
    fun getMeals(utcDate: String): Flow<List<MealWithConsumedFoods>>

    @Transaction
    @Query("SELECT * FROM meals WHERE id=:id LIMIT 1")
    fun getMealById(id: Int): Flow<MealWithConsumedFoods?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(mealEntities: List<MealEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeal(mealEntity: MealEntity)

    @Query("DELETE FROM meals WHERE id=:mealId")
    suspend fun deleteMeal(mealId: Int)

    @Query("DELETE FROM meals WHERE SUBSTR(utcCreatedAt, 1, 10) = SUBSTR(:utcDate, 1, 10)")
    suspend fun deleteAllByDate(utcDate: String)

    @Query("DELETE FROM meals")
    suspend fun deleteAll()

}