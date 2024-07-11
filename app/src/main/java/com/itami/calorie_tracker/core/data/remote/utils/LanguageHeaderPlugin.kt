package com.itami.calorie_tracker.core.data.remote.utils

import io.ktor.client.plugins.api.createClientPlugin
import java.util.Locale

val LanguageHeaderPlugin = createClientPlugin("LanguageHeaderPlugin") {
    onRequest { request, _ ->
        request.headers.append("Accept-Language", Locale.getDefault().language)
    }
}