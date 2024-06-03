package com.itami.calorie_tracker.reports_feature.presentation.screens.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.domain.model.User
import com.itami.calorie_tracker.core.domain.model.WeightUnit
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.components.WeightDialog
import com.itami.calorie_tracker.core.presentation.components.chart.rememberWeightMarker
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme
import com.itami.calorie_tracker.reports_feature.presentation.component.WeightItem
import com.itami.calorie_tracker.reports_feature.presentation.model.WeightUi
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisTickComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReportsScreen(
    onShowSnackbar: (message: String) -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is ReportsUiEvent.ShowSnackbar -> onShowSnackbar(event.message)
            is ReportsUiEvent.NavigateToProfile -> onNavigateToProfile()
        }
    }

    ReportsScreenContent(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onShowSnackbar = onShowSnackbar
    )
}

@Preview
@Composable
fun ReportsScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        ReportsScreenContent(
            state = ReportsState(),
            onAction = {},
            onShowSnackbar = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReportsScreenContent(
    state: ReportsState,
    onAction: (action: ReportsAction) -> Unit,
    onShowSnackbar: (message: String) -> Unit,
) {
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    if (state.showAddWeightDialog) {
        WeightDialog(
            selectedWeightUnit = state.weightUnit,
            onShowErrorMessage = onShowSnackbar,
            onChangeWeightUnit = {
                onAction(ReportsAction.ChangeWeightUnit(it))
            },
            onConfirm = {
                onAction(ReportsAction.AddWeight(it))
            },
            onDismiss = {
                onAction(ReportsAction.DismissAddWeightDialog)
            }
        )
    }

    if (state.weightToEdit != null) {
        val weight = state.weightToEdit
        WeightDialog(
            selectedWeightUnit = state.weightUnit,
            confirmText = stringResource(id = R.string.edit),
            startWeightGrams = weight.weightGrams,
            onShowErrorMessage = onShowSnackbar,
            onChangeWeightUnit = {
                onAction(ReportsAction.ChangeWeightUnit(it))
            },
            onConfirm = { weightGrams ->
                onAction(ReportsAction.EditWeight(weightGrams = weightGrams, weightId = weight.id))
            },
            onDismiss = {
                onAction(ReportsAction.DismissEditWeightDialog)
            }
        )
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
                        onProfileImageClick = {
                            onAction(ReportsAction.ProfilePictureClick)
                        },
                    )
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
            if (!state.isLoadingWeights && state.errorMessage == null) {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = CalorieTrackerTheme.padding.default),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    WeightChartSection(
                        weights = state.weights,
                        selectedWeightUnit = state.weightUnit,
                        changeWeightUnit = { unit ->
                            onAction(ReportsAction.ChangeWeightUnit(unit))
                        }
                    )
                    Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.medium))
                    ResultsSection(
                        weights = state.weights,
                        selectedWeightUnit = state.weightUnit,
                        onAddWeightClick = {
                            onAction(ReportsAction.AddWeightClick)
                        },
                        onWeightItemClick = { weight ->
                            onAction(ReportsAction.WeightClick(weight))
                        }
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
            if (state.isLoadingWeights) {
                CircularProgressIndicator(color = CalorieTrackerTheme.colors.primary)
            }
            if (state.errorMessage != null) {
                Error(
                    errorMessage = state.errorMessage,
                    onRetryClick = {
                        onAction(ReportsAction.ReloadWeights)
                    }
                )
            }
        }
    }
}

@Composable
private fun ResultsSection(
    weights: List<WeightUi>,
    selectedWeightUnit: WeightUnit,
    onAddWeightClick: () -> Unit,
    onWeightItemClick: (weight: WeightUi) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.results_label),
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.onBackground,
            )
            IconButton(
                onClick = { onAddWeightClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add),
                    contentDescription = stringResource(id = R.string.desc_icon_add),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.default))

        val weightItemHeight = 44.dp
        val bottomSpacerHeight = 80.dp
        val lazyColumnHeight = remember(weights.size) {
            weightItemHeight * weights.size + bottomSpacerHeight
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = lazyColumnHeight),
            verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = false,
        ) {
            itemsIndexed(items = weights, key = { index, item -> item.id }) { index, weight ->
                WeightItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(weightItemHeight),
                    weight = weight,
                    weightUnit = selectedWeightUnit,
                    onClick = {
                        onWeightItemClick(weight)
                    }
                )
                if (index == weights.size - 1) {
                    Spacer(modifier = Modifier.height(bottomSpacerHeight))
                }
            }
        }
    }
}

@Composable
private fun WeightChartSection(
    weights: List<WeightUi>,
    selectedWeightUnit: WeightUnit,
    changeWeightUnit: (weightUnit: WeightUnit) -> Unit,
) {
    val xData = remember(weights, selectedWeightUnit) {
        List(weights.size) { index ->
            index
        }
    }

    val yData = remember(weights, selectedWeightUnit) {
        weights.map {
            when (selectedWeightUnit) {
                WeightUnit.POUND -> {
                    it.weightPounds
                }

                WeightUnit.KILOGRAM -> {
                    it.weightKgs
                }
            }
        }
    }

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(weights, selectedWeightUnit) {
        withContext(Dispatchers.Default) {
            modelProducer.tryRunTransaction {
                lineSeries {
                    series(x = xData, y = yData)
                }
            }
        }
    }

    val scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.End)

    val zoomState = rememberVicoZoomState()

    val marker = rememberWeightMarker(labelPosition = DefaultCartesianMarker.LabelPosition.Top)

    val cartesianChart = rememberCartesianChart(
        rememberLineCartesianLayer(
            lines = listOf(rememberLineSpec(shader = DynamicShader.color(CalorieTrackerTheme.colors.primary))),
            axisValueOverrider = remember {
                AxisValueOverrider.adaptiveYValues(
                    yFraction = 1.1f,
                    round = true
                )
            }
        ),
        startAxis = rememberStartAxis(
            label = rememberAxisLabelComponent(color = CalorieTrackerTheme.colors.onBackground),
            axis = rememberAxisLineComponent(color = CalorieTrackerTheme.colors.onBackgroundVariant),
            guideline = rememberAxisGuidelineComponent(color = CalorieTrackerTheme.colors.onBackgroundVariant),
            tick = rememberAxisTickComponent(
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                brush = null
            ),
        ),
        bottomAxis = rememberBottomAxis(
            label = rememberAxisLabelComponent(color = CalorieTrackerTheme.colors.onBackground),
            axis = rememberAxisLineComponent(color = CalorieTrackerTheme.colors.onBackgroundVariant),
            tick = rememberAxisTickComponent(
                color = CalorieTrackerTheme.colors.onBackgroundVariant,
                brush = null
            ),
            guideline = null,
            valueFormatter = remember(weights) {
                CartesianValueFormatter { value, chartValues, verticalAxisPosition ->
                    weights[value.toInt()].datetime.format(
                        DateTimeFormatter.ofPattern(
                            "MMM d, yyyy",
                            Locale.ENGLISH
                        )
                    )
                }
            }
        ),
        persistentMarkers = mapOf(7f to marker),
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            var showDropdownMenu by rememberSaveable {
                mutableStateOf(false)
            }
            Text(
                text = stringResource(R.string.weight_chart_label),
                style = CalorieTrackerTheme.typography.labelLarge,
                color = CalorieTrackerTheme.colors.onBackground,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(
                        id = when (selectedWeightUnit) {
                            WeightUnit.POUND -> {
                                R.string.weight_unit_short_pounds
                            }

                            WeightUnit.KILOGRAM -> {
                                R.string.weight_unit_short_kilograms
                            }
                        }
                    ),
                    style = CalorieTrackerTheme.typography.labelLarge,
                    color = CalorieTrackerTheme.colors.onBackground,
                )
                IconButton(
                    onClick = {
                        showDropdownMenu = true
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_arrow_drop_down),
                        contentDescription = stringResource(R.string.icon_desc_arrow_drop_down),
                        tint = CalorieTrackerTheme.colors.onBackground,
                        modifier = Modifier.size(24.dp)
                    )
                }
                DropdownMenu(
                    modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                    expanded = showDropdownMenu,
                    onDismissRequest = {
                        showDropdownMenu = false
                    }
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                        text = {
                            Text(
                                text = stringResource(id = R.string.weight_unit_short_kilograms),
                                style = CalorieTrackerTheme.typography.labelLarge,
                                color = CalorieTrackerTheme.colors.onSurfacePrimary,
                            )
                        },
                        onClick = {
                            showDropdownMenu = false
                            changeWeightUnit(WeightUnit.KILOGRAM)
                        }
                    )
                    DropdownMenuItem(
                        modifier = Modifier.background(CalorieTrackerTheme.colors.surfacePrimary),
                        text = {
                            Text(
                                text = stringResource(id = R.string.weight_unit_short_pounds),
                                style = CalorieTrackerTheme.typography.labelLarge,
                                color = CalorieTrackerTheme.colors.onSurfacePrimary,
                            )
                        },
                        onClick = {
                            showDropdownMenu = false
                            changeWeightUnit(WeightUnit.POUND)
                        }
                    )
                }
            }
        }
        CartesianChartHost(
            modifier = Modifier.height(250.dp),
            chart = cartesianChart,
            modelProducer = modelProducer,
            scrollState = scrollState,
            zoomState = zoomState,
            marker = marker
        )
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
                error = painterResource(id = R.drawable.unknown_person),
                placeholder = painterResource(id = R.drawable.unknown_person),
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