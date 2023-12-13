package com.itami.calorie_tracker.diary_feature.domain.use_case

import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.diary_feature.domain.model.Food
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository

class SearchFoodUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(
        query: String,
        page: Int,
        pageSize: Int = 20,
    ): AppResponse<List<Food>> {
        return diaryRepository.searchFood(query, page, pageSize)
    }

}