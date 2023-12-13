package com.itami.calorie_tracker.core.presentation.components.animated_bottom_bar.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun animatedNavBarMultipleMeasurePolicy(
    selectedItemIndex: Int,
    ballSize: Dp,
    onBallPositionsCalculated: (ArrayList<Float>) -> Unit,
) = remember(selectedItemIndex, ballSize) {
    barMultipleMeasurePolicy(
        selectedItemIndex = selectedItemIndex,
        ballSize = ballSize,
        onBallPositionsCalculated = onBallPositionsCalculated
    )
}

internal fun barMultipleMeasurePolicy(
    selectedItemIndex: Int,
    ballSize: Dp,
    onBallPositionsCalculated: (ArrayList<Float>) -> Unit,
) = MultiContentMeasurePolicy { measurables, constraints ->
    val maxHeight = constraints.maxHeight
    val maxWidth = constraints.maxWidth

    val ballMeasurable = measurables[0][0]
    val navItemMeasurables = measurables[1]

    val navItemWidth = (maxWidth / navItemMeasurables.size) / 2
    val navItemPlaceables = navItemMeasurables.map { measurable ->
        measurable.measure(constraints.copy(maxWidth = navItemWidth, minWidth = navItemWidth))
    }
    val navItemsGap = calculateGap(navItemPlaceables, maxWidth)

    layout(maxWidth, maxHeight) {
        val selectedNavItemYPosition = (maxHeight / 3.5f).roundToInt()
        val unselectedNavItemYPosition = (maxHeight / 2.5f).roundToInt()
        val ballYPosition = (maxHeight / 5f).roundToInt()
        var navItemXPosition = navItemsGap
        val navItemsXPositions = arrayListOf<Float>()

        val ballPlaceable = ballMeasurable.measure(
            Constraints(
                maxWidth = ballSize.roundToPx(),
                maxHeight = ballSize.roundToPx()
            )
        )
        ballPlaceable.place(
            position = IntOffset(0, ballYPosition)
        )

        navItemPlaceables.forEachIndexed { index, placeable ->
            val selected = index == selectedItemIndex
            val yPosition = if (selected) selectedNavItemYPosition else unselectedNavItemYPosition
            navItemPlaceables[index].placeRelative(navItemXPosition, yPosition)

            navItemsXPositions.add(
                element = calculatePointPosition(
                    navItemXPosition,
                    navItemPlaceables[index].width,
                )
            )

            navItemXPosition += navItemPlaceables[index].width + navItemsGap
        }
        onBallPositionsCalculated(navItemsXPositions)
    }
}

private fun calculatePointPosition(xButtonPosition: Int, buttonWidth: Int): Float {
    return xButtonPosition + (buttonWidth / 2f)
}

private fun calculateGap(placeables: List<Placeable>, width: Int): Int {
    var allWidth = 0
    placeables.forEach { placeable ->
        allWidth += placeable.width
    }
    return (width - allWidth) / (placeables.size + 1)
}