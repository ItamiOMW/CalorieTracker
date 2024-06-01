package com.itami.calorie_tracker.core.utils

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException
import java.io.InputStream

fun Uri.getInputStreamFromUri(context: Context): InputStream? {
    return try {
        context.contentResolver.openInputStream(this)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
}