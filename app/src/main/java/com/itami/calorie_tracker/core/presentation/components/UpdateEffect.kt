package com.itami.calorie_tracker.core.presentation.components

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope

/**
 * The same as [LaunchedEffect], but skips first invocation.
 */
@Composable
fun UpdateEffect(key: Any?, block: suspend CoroutineScope.() -> Unit) {
    var isTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(key) {
        if (isTriggered) {
            block()
        } else {
            isTriggered = true
        }
    }
}