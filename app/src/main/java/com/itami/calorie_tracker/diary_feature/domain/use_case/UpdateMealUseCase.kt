package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.exceptions.MealException
import com.itami.calorie_tracker.diary_feature.domain.model.UpdateMeal
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class UpdateMealUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(mealId: Int, updateMeal: UpdateMeal): AppResponse<Unit> {
        val trimmedMealName = updateMeal.name.trim()
        if (trimmedMealName.isBlank()) {
            return AppResponse.failed(MealException.EmptyMealNameException)
        }
        return diaryRepository.updateMeal(
            mealId = mealId,
            updateMeal = updateMeal.copy(name = trimmedMealName)
        )
    }

}