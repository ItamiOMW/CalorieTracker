package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.exceptions.EmptyMealNameException
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class UpdateMealUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(mealId: Int, updateMeal: UpdateMeal): AppResponse<Unit> {
        if (updateMeal.name.isBlank()) {
            return AppResponse.failed(EmptyMealNameException)
        }
        return diaryRepository.updateMeal(mealId = mealId, updateMeal = updateMeal)
    }

}