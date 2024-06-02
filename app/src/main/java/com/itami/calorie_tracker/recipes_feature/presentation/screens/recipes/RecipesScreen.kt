package com.itami.calorie_tracker.recipes_feature.presentation.screens.recipes

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.presentation.components.SelectableButton
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.PullRefreshIndicator
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.pullRefresh
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.rememberPullRefreshState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.recipes_feature.domain.model.CaloriesFilter
import com.itami.calorie_tracker.recipes_feature.domain.model.Recipe
import com.itami.calorie_tracker.recipes_feature.domain.model.TimeFilter
import com.itami.calorie_tracker.recipes_feature.presentation.components.RecipeItem
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onNavigateToRecipeDetail: (recipeId: Int) -> Unit,
    onNavigateToProfile: () -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    state: RecipesState,
    uiEvent: Flow<RecipesUiEvent>,
    onAction: (action: RecipesAction) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is RecipesUiEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }
            }
        }
    }

    val recipesLazyListState = rememberLazyListState()

    LaunchedEffect(key1 = recipesLazyListState.canScrollForward) {
        if (!recipesLazyListState.canScrollForward && !state.isLoadingNextRecipes && !state.endReached && !state.isRefreshingRecipes) {
            onAction(RecipesAction.LoadNextRecipes)
        }
    }

    val refreshState = rememberPullRefreshState(
        refreshing = state.isRefreshingRecipes,
        onRefresh = {
            onAction(RecipesAction.Refresh)
        }
    )

    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.showFilterOverlay) {
        ModalBottomSheet(
            containerColor = CalorieTrackerTheme.colors.surfacePrimary,
            sheetState = bottomSheetState,
            onDismissRequest = {
                onAction(RecipesAction.ShowFilterOverlay(false))
            }
        ) {
            BottomSheetContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CalorieTrackerTheme.padding.default),
                caloriesFilters = state.caloriesFilters,
                timeFilters = state.timeFilters,
                onConfirm = { caloriesFilters, timeFilters ->
                    onAction(RecipesAction.ShowFilterOverlay(show = false))
                    onAction(RecipesAction.UpdateFilters(timeFilters, caloriesFilters))
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = topBarScrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CalorieTrackerTheme.colors.background,
                    titleContentColor = CalorieTrackerTheme.colors.onBackground,
                    scrolledContainerColor = CalorieTrackerTheme.colors.background
                ),
                title = {
                    TopBarContent(
                        user = state.user,
                        onProfileImageClick = onNavigateToProfile,
                    )
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(refreshState, enabled = !state.isRefreshingRecipes),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier.padding(
                    start = CalorieTrackerTheme.padding.default,
                    end = CalorieTrackerTheme.padding.default,
                ),
            ) {
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                SearchSection(
                    modifier = Modifier.fillMaxWidth(),
                    queryState = state.searchQuery,
                    onQueryChange = { newValue ->
                        onAction(RecipesAction.SearchQueryChange(newValue))
                    },
                    onFilterClick = {
                        onAction(RecipesAction.ShowFilterOverlay(show = true))
                    }
                )
                Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                RecipesSection(
                    recipes = state.recipes,
                    recipesLazyListState = recipesLazyListState,
                    isLoadingNextRecipes = state.isLoadingNextRecipes,
                    modifier = Modifier.fillMaxWidth(),
                    onRecipeClick = { recipe ->
                        onNavigateToRecipeDetail(recipe.id)
                    }
                )
            }
            if (state.isLoadingNextRecipes && state.recipes.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = CalorieTrackerTheme.colors.primary
                )
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshingRecipes,
                state = refreshState,
                contentColor = CalorieTrackerTheme.colors.primary,
                backgroundColor = CalorieTrackerTheme.colors.surfacePrimary
            )
        }
    }
}

@Composable
private fun RecipesSection(
    recipes: List<Recipe>,
    recipesLazyListState: LazyListState,
    isLoadingNextRecipes: Boolean,
    modifier: Modifier = Modifier,
    onRecipeClick: (recipe: Recipe) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = recipesLazyListState,
        verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(items = recipes, key = { _, recipe -> recipe.id }) { index, recipe ->
            RecipeItem(
                recipe = recipe,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onRecipeClick(recipe) }
            )
            if (index == recipes.size - 1) {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        item {
            if (isLoadingNextRecipes && recipes.isNotEmpty()) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun SearchSection(
    modifier: Modifier,
    queryState: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.default),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .weight(1f)
                .clip(CalorieTrackerTheme.shapes.medium),
            value = queryState,
            onValueChange = onQueryChange,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedTextColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                unfocusedTextColor = CalorieTrackerTheme.colors.onSurfacePrimary,
                focusedPlaceholderColor = CalorieTrackerTheme.colors.onSurfaceVariant,
                unfocusedPlaceholderColor = CalorieTrackerTheme.colors.onSurfaceVariant,
                unfocusedContainerColor = CalorieTrackerTheme.colors.surfacePrimary,
                focusedContainerColor = CalorieTrackerTheme.colors.surfacePrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CalorieTrackerTheme.colors.primary
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = stringResource(R.string.desc_search_icon),
                    tint = CalorieTrackerTheme.colors.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_search_for_recipes),
                    style = CalorieTrackerTheme.typography.bodySmall,
                    color = CalorieTrackerTheme.colors.onSurfaceVariant,
                    textAlign = TextAlign.Start
                )
            }
        )
        FilledIconButton(
            onClick = onFilterClick,
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = CalorieTrackerTheme.colors.surfacePrimary),
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_filter),
                contentDescription = stringResource(R.string.desc_filter_icon),
                tint = CalorieTrackerTheme.colors.primary,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    caloriesFilters: List<CaloriesFilter>,
    timeFilters: List<TimeFilter>,
    modifier: Modifier = Modifier,
    onConfirm: (caloriesFilters: List<CaloriesFilter>, timeFilters: List<TimeFilter>) -> Unit,
) {
    var selectedCaloriesFilters by remember {
        mutableStateOf(caloriesFilters)
    }
    var selectedTimeFilters by remember {
        mutableStateOf(timeFilters)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.calories_per_serving),
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.onSurfacePrimary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            ) {
                items(items = CaloriesFilter.entries, key = { it.ordinal }) { caloriesFilter ->
                    val selected = caloriesFilter in selectedCaloriesFilters
                    val text = when (caloriesFilter) {
                        CaloriesFilter.LessThan100Cal -> {
                            stringResource(R.string.filter_less_than_100cal)
                        }

                        CaloriesFilter.Between100And250Cal -> {
                            stringResource(R.string.filter_between_100_and_250cal)
                        }

                        CaloriesFilter.Between250And500Cal -> {
                            stringResource(R.string.filter_between_250_and_500cal)
                        }

                        CaloriesFilter.MoreThan500Cal -> {
                            stringResource(R.string.filter_more_than_500cal)
                        }
                    }
                    SelectableButton(
                        selected = selected,
                        text = text,
                        modifier = Modifier,
                        onClick = {
                            selectedCaloriesFilters =
                                if (selected) selectedCaloriesFilters - caloriesFilter
                                else selectedCaloriesFilters + caloriesFilter
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.large))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.time_cooking),
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.onSurfacePrimary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            ) {
                items(items = TimeFilter.entries, key = { it.ordinal }) { timeFilter ->
                    val selected = timeFilter in selectedTimeFilters
                    val text = when (timeFilter) {
                        TimeFilter.LessThan15Min -> {
                            stringResource(R.string.filter_less_than_15min)
                        }

                        TimeFilter.Between15And30Min -> {
                            stringResource(R.string.filter_between_15_and_30min)
                        }

                        TimeFilter.Between30And60Min -> {
                            stringResource(R.string.filter_between_30_and_60min)
                        }

                        TimeFilter.MoreThan60Min -> {
                            stringResource(R.string.filter_more_than_60min)
                        }
                    }
                    SelectableButton(
                        selected = selected,
                        text = text,
                        modifier = Modifier,
                        onClick = {
                            selectedTimeFilters = if (selected) selectedTimeFilters - timeFilter
                            else selectedTimeFilters + timeFilter
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraLarge))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = CalorieTrackerTheme.colors.onPrimary,
                containerColor = CalorieTrackerTheme.colors.primary
            ),
            shape = CalorieTrackerTheme.shapes.medium,
            onClick = {
                onConfirm(selectedCaloriesFilters, selectedTimeFilters)
            }
        ) {
            Text(
                text = stringResource(R.string.done),
                textAlign = TextAlign.Center,
                style = CalorieTrackerTheme.typography.labelLarge,
            )
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraLarge))
    }
}

@Composable
private fun TopBarContent(
    user: User,
    onProfileImageClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CalorieTrackerTheme.padding.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier.weight(1f, fill = false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small)
        ) {
            AsyncImage(
                model = user.profilePictureUrl,
                contentDescription = stringResource(R.string.desc_user_profile_picture),
                error = painterResource(id = R.drawable.icon_account_circle),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onProfileImageClick() }
            )
            Text(
                text = user.name,
                style = CalorieTrackerTheme.typography.titleSmall,
                color = CalorieTrackerTheme.colors.onBackground,
            )
        }
    }
}