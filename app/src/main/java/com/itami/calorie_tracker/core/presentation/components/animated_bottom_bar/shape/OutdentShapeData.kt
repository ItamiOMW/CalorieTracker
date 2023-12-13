package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.shape

import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.ShapeCornerRadius
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.shapeCornerRadius

data class OutdentShapeData(
    val xOutdent: Float = 0f,
    val height: Float = 0f,
    val width: Float = 0f,
    val cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
)