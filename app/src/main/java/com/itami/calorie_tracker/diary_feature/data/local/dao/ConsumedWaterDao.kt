package com.itami.calorie_tracker.diary_feature.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.itami.calorie_tracker.diary_feature.data.local.entity.ConsumedWaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumedWaterDao {

    @Query("SELECT * FROM consumed_water WHERE SUBSTR(timestamp, 1, 10) = SUBSTR(:utcDate, 1, 10) LIMIT 1")
    fun getConsumedWaterFlow(utcDate: String): Flow<ConsumedWaterEntity?>

    @Query("SELECT * FROM consumed_water WHERE SUBSTR(timestamp, 1, 10) = SUBSTR(:utcDate, 1, 10) LIMIT 1")
    suspend fun getConsumedWater(utcDate: String): ConsumedWaterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConsumedWater(consumedWater: ConsumedWaterEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateConsumedWater(consumedWater: ConsumedWaterEntity)

    @Query("DELETE FROM consumed_water")
    suspend fun deleteAll()

}