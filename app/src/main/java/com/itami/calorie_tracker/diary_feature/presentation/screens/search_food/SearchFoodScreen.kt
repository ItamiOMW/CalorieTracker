package com.itami.calorie_tracker.diary_feature.presentation.screens.search_food

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.PullRefreshIndicator
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.pullRefresh
import com.itami.calorie_tracker.core.presentation.components.pull_refresh.rememberPullRefreshState
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.diary_feature.domain.model.ConsumedFood
import com.itami.calorie_tracker.diary_feature.domain.model.Food
import com.itami.calorie_tracker.diary_feature.presentation.components.ConsumedFoodDialog
import com.itami.calorie_tracker.diary_feature.presentation.components.FoodItem
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchFoodScreen(
    onNavigateBack: (consumedFood: ConsumedFood?) -> Unit,
    onShowSnackbar: (message: String) -> Unit,
    state: SearchFoodState,
    uiEvent: Flow<SearchFoodUiEvent>,
    onAction: (action: SearchFoodAction) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = {
            onAction(SearchFoodAction.Refresh)
        }
    )

    val lazyListState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is SearchFoodUiEvent.ShowSnackbar -> {
                    onShowSnackbar(event.message)
                }
            }
        }
    }

    LaunchedEffect(key1 = lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward && !state.isLoadingNextPage && !state.endReached && !state.isRefreshing) {
            onAction(SearchFoodAction.LoadNextPage)
        }
    }

    if (state.selectedFood != null) {
        val consumedFood = state.selectedFood
        ConsumedFoodDialog(
            consumedFood = consumedFood,
            onConfirm = { weightGrams ->
                onNavigateBack(consumedFood.copy(grams = weightGrams))
                onAction(SearchFoodAction.SetSelectedFood(null))
            },
            onDismiss = {
                onAction(SearchFoodAction.SetSelectedFood(null))
            }
        )
    }

    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { newValue ->
                    onAction(SearchFoodAction.SearchQueryChange(newValue))
                },
                onClearQuery = {
                    onAction(SearchFoodAction.ClearSearchQuery)
                },
                onNavigateBack = {
                    onNavigateBack(null)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pullRefresh(state = pullRefreshState, enabled = !state.isLoadingNextPage),
            contentAlignment = Alignment.Center,
        ) {
            FoodsSection(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = CalorieTrackerTheme.padding.extraSmall),
                foods = state.foods,
                onFoodClick = { food ->
                    onAction(SearchFoodAction.SetSelectedFood(food))
                },
                isLoadingNextPage = state.isLoadingNextPage,
                lazyListState = lazyListState
            )
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                backgroundColor = CalorieTrackerTheme.colors.surfacePrimary,
                contentColor = CalorieTrackerTheme.colors.primary
            )
            if (state.isLoadingNextPage && state.foods.isEmpty()) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FoodsSection(
    modifier: Modifier,
    foods: List<Food>,
    onFoodClick: (food: Food) -> Unit,
    isLoadingNextPage: Boolean,
    lazyListState: LazyListState,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState
    ) {
        items(items = foods, key = { it.id }) { food ->
            FoodItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                food = food,
                onClick = {
                    onFoodClick(food)
                }
            )
        }
        item {
            if (isLoadingNextPage && foods.isNotEmpty()) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
        }
    }
}

@Composable
private fun TopBarSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .padding(
                start = CalorieTrackerTheme.padding.default,
                end = CalorieTrackerTheme.padding.default,
                top = CalorieTrackerTheme.padding.default
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onNavigateBack
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = stringResource(R.string.desc_icon_navigate_back),
                tint = CalorieTrackerTheme.colors.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f, fill = true),
            value = searchQuery,
            onValueChange = { newValue ->
                onSearchQueryChange(newValue)
            },
            textStyle = CalorieTrackerTheme.typography.titleSmall,
            colors = TextFieldDefaults.colors(
                focusedTextColor = CalorieTrackerTheme.colors.onBackground,
                unfocusedTextColor = CalorieTrackerTheme.colors.onBackground,
                focusedPlaceholderColor = CalorieTrackerTheme.colors.onBackgroundVariant,
                unfocusedPlaceholderColor = CalorieTrackerTheme.colors.onBackgroundVariant,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = CalorieTrackerTheme.colors.primary
            ),
            maxLines = 1,
            placeholder = {
                Text(
                    text = stringResource(R.string.hint_search_food),
                    style = CalorieTrackerTheme.typography.bodyLarge,
                    color = CalorieTrackerTheme.colors.onBackgroundVariant,
                    textAlign = TextAlign.Center
                )
            },
        )
        if (searchQuery.isNotEmpty()) {
            IconButton(
                onClick = onClearQuery
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = stringResource(R.string.desc_icon_close),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}