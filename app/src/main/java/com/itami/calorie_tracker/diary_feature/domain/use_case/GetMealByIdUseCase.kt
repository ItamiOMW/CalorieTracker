package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.diary_feature.domain.model.Meal
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow

class GetMealByIdUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(mealId: Int): Flow<Meal?> {
        return diaryRepository.getMeal(mealId = mealId)
    }

}