package com.itami.calorie_tracker.recipes_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe

@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    color: Color = CalorieTrackerTheme.colors.surfacePrimary,
    contentColor: Color = CalorieTrackerTheme.colors.onSurfacePrimary,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    imageHeightDp: Dp = 200.dp,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = color,
        contentColor = contentColor,
        shape = shape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = stringResource(R.string.desc_recipe_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeightDp),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = CalorieTrackerTheme.padding.small,
                        horizontal = CalorieTrackerTheme.padding.small
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(weight = 1f, fill = false),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
                ) {
                    Text(
                        text = recipe.name,
                        style = CalorieTrackerTheme.typography.titleSmall,
                        color = CalorieTrackerTheme.colors.onSurfacePrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = stringResource(id = R.string.cal_amount, recipe.caloriesPerServing),
                        style = CalorieTrackerTheme.typography.bodySmall,
                        color = CalorieTrackerTheme.colors.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .weight(weight = 1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.tiny)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_schedule),
                        contentDescription = stringResource(R.string.desc_time_icon),
                        tint = CalorieTrackerTheme.colors.onSurfaceVariant,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = stringResource(
                            id = R.string.minutes_number_short,
                            recipe.timeMinutes
                        ),
                        style = CalorieTrackerTheme.typography.bodySmall,
                        color = CalorieTrackerTheme.colors.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}