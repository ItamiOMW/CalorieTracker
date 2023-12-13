package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.animation.ball_trajectory

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp

/**
 * Interface defining the ball animation
 */
interface BallAnimation {

    /**
     *@param [targetOffset] target offset
     */
    @Composable
    fun animateAsState(targetOffset: Offset, ballSize: Dp): State<BallAnimInfo>
}

/**
 * Describes parameters of the ball animation
 */
data class BallAnimInfo(
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero
)