package com.itami.calorie_tracker.core.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class CalorieTrackerShapes(
    val small: Shape = RoundedCornerShape(10.dp),
    val medium: Shape = RoundedCornerShape(15.dp),
    val large: Shape = RoundedCornerShape(24.dp),
)