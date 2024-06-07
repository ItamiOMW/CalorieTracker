package com.itami.calorie_tracker.profile_feature.presentation.screens.about_app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.itami.calorie_tracker.R
import com.itami.calorie_tracker.core.domain.model.Theme
import com.itami.calorie_tracker.core.presentation.components.ListStyleText
import com.itami.calorie_tracker.core.presentation.components.ObserveAsEvents
import com.itami.calorie_tracker.core.presentation.theme.CalorieTrackerTheme

@Composable
fun AboutAppScreen(
    onNavigateBack: () -> Unit,
    viewModel: AboutAppViewModel = hiltViewModel(),
) {
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is AboutAppUiEvent.NavigateBack -> onNavigateBack()
        }
    }

    AboutAppScreenContent(onAction = viewModel::onAction)
}

@Preview
@Composable
fun AboutAppScreenContentPreview() {
    CalorieTrackerTheme(theme = Theme.SYSTEM_THEME) {
        AboutAppScreenContent(
            onAction = {}
        )
    }
}

@Composable
private fun AboutAppScreenContent(
    onAction: (AboutAppAction) -> Unit,
) {
    Scaffold(
        containerColor = CalorieTrackerTheme.colors.background,
        contentColor = CalorieTrackerTheme.colors.onBackground,
        topBar = {
            TopBarSection(
                onNavigateBackClick = {
                    onAction(AboutAppAction.NavigateBackClick)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    top = CalorieTrackerTheme.padding.large,
                    start = CalorieTrackerTheme.padding.default,
                    end = CalorieTrackerTheme.padding.default,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.calorie_tracker_about_app),
                style = CalorieTrackerTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraLarge))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.medium)
            ) {
                Text(
                    text = stringResource(R.string.provide_features_such_as),
                    style = CalorieTrackerTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                ListStyleText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.desc_diary_feature)
                )
                ListStyleText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.desc_reports_feature)
                )
                ListStyleText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.desc_recipes_feature)
                )
            }
            Spacer(modifier = Modifier.height(CalorieTrackerTheme.spacing.extraLarge))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(CalorieTrackerTheme.spacing.default),
            ) {
                OutlinedFeatureItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(95.dp),
                    featureTitle = stringResource(id = R.string.diary),
                    featureIcon = painterResource(id = R.drawable.icon_pie_chart)
                )
                OutlinedFeatureItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(95.dp),
                    featureTitle = stringResource(id = R.string.reports),
                    featureIcon = painterResource(id = R.drawable.icon_progress)
                )
                OutlinedFeatureItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(95.dp),
                    featureTitle = stringResource(id = R.string.recipes),
                    featureIcon = painterResource(id = R.drawable.icon_menu_book)
                )
            }
        }
    }
}

@Composable
private fun OutlinedFeatureItem(
    modifier: Modifier,
    featureTitle: String,
    featureIcon: Painter,
    shape: Shape = CalorieTrackerTheme.shapes.small,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = CalorieTrackerTheme.colors.primary,
    borderStroke: BorderStroke = BorderStroke(
        width = 2.dp,
        color = CalorieTrackerTheme.colors.primary
    ),
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = borderStroke
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = featureIcon,
                contentDescription = featureTitle,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = featureTitle,
                style = CalorieTrackerTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(
    onNavigateBackClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = CalorieTrackerTheme.padding.small),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = CalorieTrackerTheme.colors.background,
            titleContentColor = CalorieTrackerTheme.colors.onBackground,
            navigationIconContentColor = CalorieTrackerTheme.colors.onBackground
        ),
        title = {
            Text(
                text = stringResource(R.string.title_about_app),
                style = CalorieTrackerTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = CalorieTrackerTheme.colors.onBackground
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier,
                onClick = onNavigateBackClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_back),
                    contentDescription = stringResource(R.string.desc_icon_navigate_back),
                    tint = CalorieTrackerTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}