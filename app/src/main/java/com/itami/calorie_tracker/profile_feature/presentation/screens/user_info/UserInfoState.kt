package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info

import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit

data class UserInfoState(
    val user: User = User.DEFAULT,
    val sheetContent: UserInfoScreenSheetContent? = null,
    val weightUnit: WeightUnit = WeightUnit.KILOGRAM,
    val heightUnit: HeightUnit = HeightUnit.METER,
    val showSaveDialog: Boolean = false,
    val isLoading: Boolean = false,
)

enum class UserInfoScreenSheetContent {
    GOAL, AGE, HEIGHT, WEIGHT, GENDER, LIFESTYLE;
}
