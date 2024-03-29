package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.exceptions.MealException
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class AddConsumedWaterUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(waterMl: Int, date: String): AppResponse<Unit> {
        if (waterMl <= 0 || waterMl > 3000) {
            return AppResponse.failed(MealException.InvalidWaterMlException)
        }
        return diaryRepository.addConsumedWater(waterMl = waterMl, date = date)
    }

}