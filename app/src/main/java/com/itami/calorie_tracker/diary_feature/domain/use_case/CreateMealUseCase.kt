package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.exceptions.EmptyMealNameException
import com.itami.calorie_tracker.diary_feature.domain.model.CreateMeal
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class CreateMealUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(createMeal: CreateMeal): AppResponse<Unit> {
        if (createMeal.name.isBlank()) {
            return AppResponse.failed(EmptyMealNameException)
        }
        return diaryRepository.createMeal(createMeal)
    }

}