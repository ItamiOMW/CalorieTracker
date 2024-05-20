package com.itami.calorie_tracker.reports_feature.data.mapper

import com.itami.calorie_tracker.reports_feature.data.remote.dto.response.WeightResponse
import com.itami.calorie_tracker.reports_feature.domain.model.Weight

fun WeightResponse.toWeight() = Weight(
    id = this.id,
    weightGrams = this.weightGrams,
    datetime = this.datetime
)