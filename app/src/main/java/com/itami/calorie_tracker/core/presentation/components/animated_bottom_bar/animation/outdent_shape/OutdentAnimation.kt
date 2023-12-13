package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.utils.toPxf

interface OutdentAnimation {

    @Composable
    fun animateOutdentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius
    ): State<Shape>
}

data class ShapeCornerRadius(
    val topLeft: Float,
    val topRight: Float,
    val bottomRight: Float,
    val bottomLeft: Float,
)

fun shapeCornerRadius(cornerRadius: Float) =
    ShapeCornerRadius(
        topLeft = cornerRadius,
        topRight = cornerRadius,
        bottomRight = cornerRadius,
        bottomLeft = cornerRadius
    )

@Composable
fun shapeCornerRadius(cornerRadius: Dp) =
    ShapeCornerRadius(
        topLeft = cornerRadius.toPxf(),
        topRight = cornerRadius.toPxf(),
        bottomRight = cornerRadius.toPxf(),
        bottomLeft = cornerRadius.toPxf()
    )

@Composable
fun shapeCornerRadius(
    topLeft: Dp,
    topRight: Dp,
    bottomRight: Dp,
    bottomLeft: Dp
) = ShapeCornerRadius(
    topLeft = topLeft.toPxf(),
    topRight = topRight.toPxf(),
    bottomRight = bottomRight.toPxf(),
    bottomLeft = bottomLeft.toPxf()
)