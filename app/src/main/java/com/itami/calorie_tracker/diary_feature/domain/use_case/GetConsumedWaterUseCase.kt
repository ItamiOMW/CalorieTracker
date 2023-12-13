package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedWater
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow

class GetConsumedWaterUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(date: String): Flow<ConsumedWater?> {
        return diaryRepository.getConsumedWater(date = date)
    }

}