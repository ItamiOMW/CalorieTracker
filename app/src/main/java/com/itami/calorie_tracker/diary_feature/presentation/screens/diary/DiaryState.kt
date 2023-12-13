package com.itami.calorie_tracker.diary_feature.presentation.screens.diary

import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.diary_feature.presentation.model.ConsumedWaterUi
import com.itami.calorie_tracker.diary_feature.presentation.model.MealUi
import java.time.ZonedDateTime

data class DiaryState(
    val user: User? = null,
    val date: ZonedDateTime = DateTimeUtil.getCurrentZonedDateTime(),

    val meals: List<MealUi> = emptyList(),
    val consumedProteins: Int = 0,
    val consumedFats: Int = 0,
    val consumedCarbs: Int = 0,
    val consumedCalories: Int = 0,
    val consumedWater: ConsumedWaterUi? = null,

    val messageError: String? = null,
    val showDatePicker: Boolean = false,
    val isLoading: Boolean = false,
    val isRefreshingMeals: Boolean = false,
    val isLoadingMeals: Boolean = false,
)
