package com.itami.calorie_tracker.reports_feature.presentation.mapper

import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.utils.DateTimeUtil
import com.itami.calorie_tracker.reports_feature.domain.model.Weight
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi

fun Weight.toWeightUi() = WeightUi(
    id = this.id,
    weightGrams = this.weightGrams,
    weightKgs = WeightUnit.KILOGRAM.convert(this.weightGrams),
    weightPounds = WeightUnit.POUND.convert(this.weightGrams),
    datetime = DateTimeUtil.stringToDateTime(this.datetime),
)