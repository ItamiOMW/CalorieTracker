package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.ball_trajectory.BallAnimInfo
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.ball_trajectory.BallAnimation
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.ball_trajectory.StraightBall
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.OutdentAnimation
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.ShapeCornerRadius
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.StraightOutdent
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.outdent_shape.shapeCornerRadius
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.layout.animatedNavBarMultipleMeasurePolicy
import com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.utils.ballTransform

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color = Color.White,
    ballColor: Color = Color.Black,
    ballSize: Dp = 64.dp,
    cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
    outdentAnimation: OutdentAnimation = StraightOutdent(tween(500)),
    ballAnimation: BallAnimation = StraightBall(tween(500)),
    content: @Composable () -> Unit,
) {
    var lastItemIndex by remember { mutableIntStateOf(0) }
    var navItemsPositions by remember { mutableStateOf(listOf<Offset>()) }
    val multipleMeasurePolicy = animatedNavBarMultipleMeasurePolicy(
        selectedItemIndex = selectedIndex,
        ballSize = ballSize
    ) {
        navItemsPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    val selectedNavItemOffset by remember(selectedIndex, navItemsPositions) {
        derivedStateOf {
            if (navItemsPositions.isEmpty()) {
                return@derivedStateOf Offset.Unspecified
            }
            if (selectedIndex == -1) {
                return@derivedStateOf navItemsPositions[lastItemIndex]
            }
            lastItemIndex = selectedIndex
            return@derivedStateOf navItemsPositions[selectedIndex]
        }
    }

    val indentShape = outdentAnimation.animateOutdentShapeAsState(
        shapeCornerRadius = cornerRadius,
        targetOffset = selectedNavItemOffset
    )

    val ballAnimInfoState = ballAnimation.animateAsState(
        targetOffset = selectedNavItemOffset,
        ballSize
    )

    Layout(
        modifier = modifier
            .graphicsLayer {
                clip = true
                shape = indentShape.value
            }
            .background(barColor),
        contents = listOf(
            {
                ColorBall(
                    ballColor = ballColor,
                    ballAnimInfo = ballAnimInfoState.value,
                    sizeDp = ballSize
                )
            },
            content
        ),
        measurePolicy = multipleMeasurePolicy
    )
}

@Composable
private fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    ballAnimInfo: BallAnimInfo,
    sizeDp: Dp,
) {
    Box(
        modifier = modifier
            .ballTransform(ballAnimInfo)
            .size(sizeDp)
            .clip(shape = CircleShape)
            .background(ballColor)
    )
}