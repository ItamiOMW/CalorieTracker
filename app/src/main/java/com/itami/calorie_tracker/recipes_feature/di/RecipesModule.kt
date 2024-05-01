package com.itami.calorie_tracker.recipes_feature.di

import com.itami.calorie_tracker.recipes_feature.data.remote.RecipesApiService
import com.itami.calorie_tracker.recipes_feature.data.remote.RecipesApiServiceImpl
import com.itami.calorie_tracker.recipes_feature.data.repository.RecipesRepositoryImpl
import com.itami.calorie_tracker.recipes_feature.domain.repository.RecipesRepository
import com.itami.calorie_tracker.recipes_feature.domain.use_case.GetRecipeByIdUseCase
import com.itami.calorie_tracker.recipes_feature.domain.use_case.GetRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipesModule {

    @Provides
    @Singleton
    fun provideGetRecipesUseCase(
        recipesRepository: RecipesRepository,
    ) = GetRecipesUseCase(recipesRepository)

    @Provides
    @Singleton
    fun provideGetRecipeByIdUseCase(
        recipesRepository: RecipesRepository,
    ) = GetRecipeByIdUseCase(recipesRepository)


    @Provides
    @Singleton
    fun provideRecipesRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl,
    ): RecipesRepository = recipesRepositoryImpl

    @Provides
    @Singleton
    fun provideRecipesApiService(
        recipesApiServiceImpl: RecipesApiServiceImpl
    ): RecipesApiService = recipesApiServiceImpl

}