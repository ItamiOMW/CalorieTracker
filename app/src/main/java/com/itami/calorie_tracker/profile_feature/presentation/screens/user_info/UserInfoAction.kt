package com.itami.calorie_tracker.profile_feature.presentation.screens.user_info

import com.itami.calorie_tracker.core.domain.model.Gender
import com.itami.calorie_tracker.core.domain.model.HeightUnit
import com.itami.calorie_tracker.core.domain.model.Lifestyle
import com.itami.calorie_tracker.core.domain.model.WeightGoal
import com.itami.calorie_tracker.core.domain.model.WeightUnit

sealed class UserInfoAction {

    data object GoalFieldClick : UserInfoAction()

    data object AgeFieldClick : UserInfoAction()

    data object HeightFieldClick : UserInfoAction()

    data object WeightFieldClick : UserInfoAction()

    data object GenderFieldClick : UserInfoAction()

    data object LifestyleFieldClick : UserInfoAction()

    data class ChangeGoal(val goal: WeightGoal) : UserInfoAction()

    data class ChangeAge(val age: Int) : UserInfoAction()

    data class ChangeHeight(val heightCm: Int) : UserInfoAction()

    data class ChangeHeightUnit(val heightUnit: HeightUnit) : UserInfoAction()

    data class ChangeWeight(val weightGrams: Int) : UserInfoAction()

    data class ChangeWeightUnit(val weightUnit: WeightUnit) : UserInfoAction()

    data class ChangeGender(val gender: Gender) : UserInfoAction()

    data class ChangeLifestyle(val lifestyle: Lifestyle) : UserInfoAction()

    data object DismissBottomSheet : UserInfoAction()

    data object NavigateBackClick : UserInfoAction()

    data object SaveClick : UserInfoAction()

    data class SaveUserConfirm(val updateNutrients: Boolean) : UserInfoAction()

    data object SaveUserDeny : UserInfoAction()

}