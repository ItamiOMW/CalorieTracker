package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipe_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.components.NutrientAmountItem
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.core.utils.fillWidthOfParent
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe

@Composable
fun RecipeDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when(event) {
            RecipeDetailsUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    RecipeDetailsScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Preview
@Composable
fun RecipeDetailsScreenContentPreview() {
    CalorieTrackerTheme {
        RecipeDetailsScreenContent(
            state = RecipeDetailState(),
            onAction = {}
        )
    }
}

@Composable
private fun RecipeDetailsScreenContent(
    state: RecipeDetailState,
    onAction: (RecipeDetailAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarContent(
                recipe = state.recipe,
                onNavigateBackClick = {
                    onAction(RecipeDetailAction.NavigateBackClick)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.recipe != null) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .padding(horizontal = CalorieTrackerTheme.padding.default),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    RecipeInfoSection(
                        recipe = state.recipe,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
                    HorizontalDivider(
                        modifier = Modifier.fillWidthOfParent(parentPadding = CalorieTrackerTheme.padding.default),
                        color = CalorieTrackerTheme.colors.outline
                    )
                    Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                    RecipeTextSection(
                        recipe = state.recipe,
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
            if (state.errorMessage != null) {
                Error(
                    errorMessage = state.errorMessage,
                    onRetryClick = {
                        onAction(RecipeDetailAction.RetryClick)
                    }
                )
            }
        }
    }
}

@Composable
private fun RecipeInfoSection(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = CalorieTrackerTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(weight = 1f, fill = false),
                text = recipe.name,
                style = CalorieTrackerTheme.typography.titleSmall,
                color = CalorieTrackerTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
            )
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
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NutrientAmountItem(
                nutrientName = stringResource(id = R.string.nutrient_name_calories),
                nutrientAmount = recipe.caloriesPerServing.toString()
            )
            NutrientAmountItem(
                nutrientName = stringResource(id = R.string.nutrient_name_proteins),
                nutrientAmount = stringResource(
                    id = R.string.amount_grams,
                    recipe.proteinsPerServing
                )
            )
            NutrientAmountItem(
                nutrientName = stringResource(id = R.string.nutrient_name_fats),
                nutrientAmount = stringResource(id = R.string.amount_grams, recipe.fatsPerServing)
            )
            NutrientAmountItem(
                nutrientName = stringResource(id = R.string.nutrient_name_carbs),
                nutrientAmount = stringResource(id = R.string.amount_grams, recipe.carbsPerServing)
            )
        }
    }
}

@Composable
private fun RecipeTextSection(
    recipe: Recipe,
) {
    val scrollState = rememberScrollState()
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        text = recipe.recipeText,
        color = CalorieTrackerTheme.colors.onBackground,
        style = CalorieTrackerTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify,
    )
}

@Composable
private fun TopBarContent(
    recipe: Recipe?,
    onNavigateBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
    ) {
        if (recipe != null) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = stringResource(id = R.string.desc_recipe_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
        FilledIconButton(
            onClick = onNavigateBackClick,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = CalorieTrackerTheme.colors.surfacePrimary,
                contentColor = CalorieTrackerTheme.colors.onSurfacePrimary
            ),
            modifier = Modifier
                .statusBarsPadding()
                .padding(
                    start = CalorieTrackerTheme.padding.default,
                    top = CalorieTrackerTheme.padding.small
                )
                .align(Alignment.TopStart),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_close),
                contentDescription = stringResource(id = R.string.desc_icon_close),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun Error(
    errorMessage: String,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            style = CalorieTrackerTheme.typography.labelLarge,
            color = CalorieTrackerTheme.colors.onBackground
        )
        IconButton(
            onClick = onRetryClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_refresh),
                contentDescription = stringResource(R.string.desc_refresh_icon),
                tint = CalorieTrackerTheme.colors.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}