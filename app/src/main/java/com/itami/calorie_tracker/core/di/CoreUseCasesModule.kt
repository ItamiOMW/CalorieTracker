package com.itami.calorie_tracker.core.di

import com.itami.calorie_tracker.authentication_feature.domain.repository.AuthRepository
import com.itami.calorie_tracker.core.domain.repository.UserManager
import com.itami.calorie_tracker.core.domain.repository.UserRepository
import com.itami.calorie_tracker.core.domain.use_case.CalculateNutrientsUseCase
import com.itami.calorie_tracker.core.domain.use_case.LogoutUseCase
import com.itami.calorie_tracker.core.domain.use_case.SetAgeUseCase
import com.itami.calorie_tracker.core.domain.use_case.SetHeightUseCase
import com.itami.calorie_tracker.core.domain.use_case.SetWeightUseCase
import com.itami.calorie_tracker.core.domain.use_case.UpdateUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreUseCasesModule {

    @Provides
    @Singleton
    fun provideSetHeightUseCase(
        userManager: UserManager,
    ) = SetHeightUseCase(userManager = userManager)

    @Provides
    @Singleton
    fun provideSetWeightUseCase(
        userManager: UserManager,
    ) = SetWeightUseCase(userManager = userManager)

    @Provides
    @Singleton
    fun provideSetAgeUseCase(
        userManager: UserManager,
    ) = SetAgeUseCase(userManager = userManager)

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ) = LogoutUseCase(authRepository)

    @Provides
    @Singleton
    fun provideCalculateAndSetNutrientsUseCase() = CalculateNutrientsUseCase()

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
        userRepository: UserRepository
    ) = UpdateUserUseCase(userRepository)
}