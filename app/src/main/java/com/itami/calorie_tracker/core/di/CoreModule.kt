package com.itami.calorie_tracker.core.di

import android.content.Context
import android.util.Log
import coil.ImageLoader
import com.itami.calorie_tracker.BuildConfig
import com.itami.calorie_tracker.core.data.auth.AuthManager
import com.itami.calorie_tracker.core.data.auth.EncryptedAuthManager
import com.itami.calorie_tracker.core.data.repository.DataStoreAppSettingsManager
import com.itami.calorie_tracker.core.data.repository.user_manager.DataStoreUserManager
import com.itami.calorie_tracker.core.domain.repository.AppSettingsManager
import com.itami.calorie_tracker.core.domain.repository.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideAuthManager(
        authManager: EncryptedAuthManager,
    ): AuthManager = authManager

    @Provides
    @Singleton
    fun provideUserManager(
        userManager: DataStoreUserManager,
    ): UserManager = userManager

    @Provides
    @Singleton
    fun provideAppSettingsManager(
        appSettings: DataStoreAppSettingsManager,
    ): AppSettingsManager = appSettings

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
    ): ImageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP_CLIENT", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors(maxRetries = 1)
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 20_000
                requestTimeoutMillis = 15_000
                socketTimeoutMillis = 20_000
            }
            defaultRequest {
                url(BuildConfig.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}