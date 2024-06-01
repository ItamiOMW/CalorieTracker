package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun ImagePickerComponent(
    imagePath: Any?,
    onAddImageButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    fabSize: Dp = 50.dp,
    imageSize: Dp = 100.dp,
    fabContainerColor: Color = CalorieTrackerTheme.colors.primary,
    iconColor: Color = CalorieTrackerTheme.colors.onPrimary,
    fabShape: Shape = RoundedCornerShape(30.dp),
    imageShape: Shape = RoundedCornerShape(180.dp),
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .size(imageSize)
                .clip(imageShape),
            model = imagePath,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.desc_chosen_profile_picture),
            placeholder = painterResource(id = R.drawable.unknown_person),
            fallback = painterResource(id = R.drawable.unknown_person),
        )
        FloatingActionButton(
            modifier = Modifier
                .size(fabSize)
                .align(Alignment.BottomEnd),
            shape = fabShape,
            containerColor = fabContainerColor,
            onClick = { onAddImageButtonClicked() },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_add_photo),
                contentDescription = stringResource(R.string.desc_add_photo_icon),
                modifier = Modifier.size(iconSize),
                tint = iconColor
            )
        }
    }

}