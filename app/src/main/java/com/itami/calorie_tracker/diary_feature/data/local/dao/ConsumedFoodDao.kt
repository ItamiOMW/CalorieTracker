package com.itami.calorie_tracker.diary_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedFoodEntity

@Dao
interface ConsumedFoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumedFoods(consumedFoodEntities: List<ConsumedFoodEntity>)

    @Query("DELETE FROM consumed_foods WHERE id=:id")
    suspend fun deleteConsumedFoodById(id: Int)

    @Query("DELETE FROM consumed_foods")
    suspend fun deleteAll()

}