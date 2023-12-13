package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.shape

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.ShapeCornerRadius

class OutdentRectShape(
    private val outdentShapeData: OutdentShapeData,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(
        Path().addRoundRectWithOutdent(size, outdentShapeData, layoutDirection)
    )

    fun copy(
        cornerRadius: ShapeCornerRadius = outdentShapeData.cornerRadius,
        xOutdent: Float = outdentShapeData.xOutdent,
        yOutdent: Float = outdentShapeData.height,
    ) = OutdentRectShape(
        outdentShapeData.copy(
            cornerRadius = cornerRadius,
            xOutdent = xOutdent,
            height = yOutdent,
        )
    )
}

fun Path.addRoundRectWithOutdent(
    size: Size,
    outdentShapeData: OutdentShapeData,
    layoutDirection: LayoutDirection,
): Path {
    val width = size.width
    val height = size.height
    val cornerRadius = outdentShapeData.cornerRadius
    val sweepAngleDegrees = 90f

    return apply {
        moveTo(chooseCornerSize(size.height, cornerRadius.topLeft), 0f)

        val xOffset = if (layoutDirection == LayoutDirection.Ltr) {
            outdentShapeData.xOutdent - outdentShapeData.width / 2
        } else {
            size.width - outdentShapeData.xOutdent - outdentShapeData.width / 2
        }

        if (xOffset > cornerRadius.topLeft / 4) {
            addPath(
                OutdentPath(
                    Rect(
                        Offset(
                            x = xOffset,
                            y = 0f
                        ),
                        Size(outdentShapeData.width, outdentShapeData.height)
                    )
                ).createPath(cornerRadius)
            )
        }

//        lineTo(width - cornerRadius.topRight, outdentShapeData.height)
//        lineTo(width - cornerRadius.topRight, height)
//        lineTo(0F + cornerRadius.bottomLeft, height)
//        lineTo(0F + cornerRadius.bottomLeft, outdentShapeData.height)

        arcTo(
            rect = Rect(
                offset = Offset(width - cornerRadius.topRight, outdentShapeData.height),
                size = Size(cornerRadius.topRight, cornerRadius.topRight)
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        arcTo(
            rect = Rect(
                offset = Offset(
                    width - cornerRadius.bottomRight,
                    height - cornerRadius.bottomRight
                ),
                size = Size(cornerRadius.bottomRight, cornerRadius.bottomRight)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        arcTo(
            rect = Rect(
                offset = Offset(0f, height - cornerRadius.bottomLeft),
                size = Size(cornerRadius.bottomLeft, cornerRadius.bottomLeft)
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        arcTo(
            rect = Rect(
                offset = Offset(0f, outdentShapeData.height),
                size = Size(cornerRadius.topLeft, cornerRadius.topLeft)
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = sweepAngleDegrees,
            forceMoveTo = false
        )
        close()
    }
}

private fun chooseCornerSize(sizeHeight: Float, cornerRadius: Float): Float {
    return if (sizeHeight > cornerRadius) {
        cornerRadius
    } else {
        sizeHeight
    }
}