package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.shape

import android.graphics.PointF
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.ShapeCornerRadius

class OutdentPath(
    private val rect: Rect,
) {
    private val maxX = 110f
    private val maxY = 34f

    private fun translate(x: Float, y: Float): PointF {
        return PointF(
            ((x / maxX) * rect.width) + rect.left,
            ((y / maxY) * rect.height) + rect.top
        )
    }

    private val mFirstCurveStartPoint = PointF()
    private val mFirstCurveControlPoint1 = PointF()
    private val mFirstCurveControlPoint2 = PointF()
    private val mFirstCurveEndPoint = PointF()

    private val mSecondCurveControlPoint1 = PointF()
    private val mSecondCurveControlPoint2 = PointF()
    private var mSecondCurveStartPoint = PointF()
    private var mSecondCurveEndPoint = PointF()


    fun createPath(shapeCornerRadius: ShapeCornerRadius): Path {
        return Path().apply {
            val middle = translate(x = 55f, y = 0f)

            val height = rect.height
            val width = rect.width

            mFirstCurveStartPoint.set(
                middle.x - (width * 2),
                height
            )
            mFirstCurveEndPoint.set(
                middle.x,
                0F
            )
            mSecondCurveStartPoint = mFirstCurveEndPoint;
            mSecondCurveEndPoint.set(
                middle.x + (width * 2),
                height
            )
            mFirstCurveControlPoint1.set(
                mFirstCurveStartPoint.x + height,
                mFirstCurveStartPoint.y
            )
            mFirstCurveControlPoint2.set(
                mFirstCurveEndPoint.x - height,
                mFirstCurveEndPoint.y
            )
            mSecondCurveControlPoint1.set(
                mSecondCurveStartPoint.x + height,
                mSecondCurveStartPoint.y
            )
            mSecondCurveControlPoint2.set(
                mSecondCurveEndPoint.x - height,
                mSecondCurveEndPoint.y
            )
            moveTo(0f, height)
            lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y)
            cubicTo(
                mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x, mFirstCurveEndPoint.y
            )
            cubicTo(
                mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y
            )
        }
    }
}