package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class DeleteMealUseCase(private val diaryRepository: DiaryRepository) {

    suspend fun deleteMeal(mealId: Int): AppResponse<Unit> {
        return diaryRepository.deleteMeal(mealId = mealId)
    }

}