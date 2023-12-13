package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class LoadMealsWithWaterUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(date: String): AppResponse<Unit> {
        return diaryRepository.loadMealsWithWater(date = date)
    }

}