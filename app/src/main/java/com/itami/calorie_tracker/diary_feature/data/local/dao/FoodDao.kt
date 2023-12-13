package com.itami.calorie_tracker.diary_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itami.calorie_tracker.diary_feature.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodEntity: FoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foodEntities: List<FoodEntity>)

    @Query("DELETE FROM foods WHERE id=:foodId")
    suspend fun delete(foodId: Int)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()

}