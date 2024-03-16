package com.itami.calorie_tracker.authentication_feature.di

import com.itami.calorie_tracker.authentication_feature.data.remote.AuthApiService
import com.itami.calorie_tracker.authentication_feature.data.remote.AuthApiServiceImpl
import com.itami.calorie_tracker.authentication_feature.data.repository.AuthRepositoryImpl
import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.authentication_feature.domain.use_case.IsAuthenticatedUseCase
import com.itami.calorie_tracker.authentication_feature.domain.use_case.LoginGoogleUseCase
import com.itami.calorie_tracker.authentication_feature.domain.use_case.RegisterGoogleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(
        authApiServiceImpl: AuthApiServiceImpl
    ): AuthApiService = authApiServiceImpl

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository = authRepositoryImpl

    @Provides
    @Singleton
    fun provideIsAuthenticatedUseCase(
        authRepository: AuthRepository
    ) = IsAuthenticatedUseCase(authRepository)

    @Provides
    @Singleton
    fun provideRegisterGoogleUseCase(
        authRepository: AuthRepository
    ) = RegisterGoogleUseCase(authRepository)

    @Provides
    @Singleton
    fun provideLoginGoogleUseCase(
        authRepository: AuthRepository
    ) = LoginGoogleUseCase(authRepository)

}