package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.shape.OutdentRectShape
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.shape.OutdentShapeData
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.utils.toPxf

@Stable
class StraightOutdent(
    private val animationSpec: FiniteAnimationSpec<Float>,
    private val outdentWidth: Dp = 25.dp,
    private val outdentHeight: Dp = 15.dp,
) : OutdentAnimation {

    @Composable
    override fun animateOutdentShapeAsState(
        targetOffset: Offset,
        shapeCornerRadius: ShapeCornerRadius
    ): State<Shape> {
        if (targetOffset.isUnspecified) {
            return remember { mutableStateOf(OutdentRectShape(OutdentShapeData())) }
        }
        val density = LocalDensity.current

        val position = animateFloatAsState(
            targetValue = targetOffset.x,
            animationSpec = animationSpec,
            label = ""
        )

        return produceState(
            initialValue = OutdentRectShape(
                outdentShapeData = OutdentShapeData(
                    xOutdent = targetOffset.x,
                    height = outdentHeight.toPxf(density),
                    width = outdentWidth.toPxf(density),
                    cornerRadius = shapeCornerRadius,
                )
            ),
            key1 = position.value,
            key2 = shapeCornerRadius
        ) {
            this.value = this.value.copy(
                xOutdent = position.value,
                cornerRadius = shapeCornerRadius
            )
        }
    }
}