package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.exceptions.InvalidWaterMlException
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class RemoveConsumedWaterUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(waterMl: Int, date: String): AppResponse<Unit> {
        if (waterMl <= 0) {
            return AppResponse.failed(InvalidWaterMlException)
        }
        return diaryRepository.removeConsumedWater(waterMl = waterMl, date = date)
    }

}