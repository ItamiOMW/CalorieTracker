package com.itami.calorie_tracker.diary_feature.di

import com.itami.calorie_tracker.diary_feature.data.remote.DiaryApiService
import com.itami.calorie_tracker.diary_feature.data.remote.DiaryApiServiceImpl
import com.itami.calorie_tracker.diary_feature.data.repository.DiaryRepositoryImpl
import com.itami.calorie_tracker.diary_feature.domain.repository.DiaryRepository
import com.itami.calorie_tracker.diary_feature.domain.use_case.AddConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.CreateMealUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.DeleteMealUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetMealByIdUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.GetMealsUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.LoadMealsWithWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.RemoveConsumedWaterUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.SearchFoodUseCase
import com.itami.calorie_tracker.diary_feature.domain.use_case.UpdateMealUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiaryModule {

    @Provides
    @Singleton
    fun provideSearchFoodUseCase(
        diaryRepository: DiaryRepository,
    ) = SearchFoodUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideGetConsumedWaterUseCase(
        diaryRepository: DiaryRepository,
    ) = GetConsumedWaterUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideAddConsumedWaterUseCase(
        diaryRepository: DiaryRepository,
    ) = AddConsumedWaterUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideRemoveConsumedWaterUseCase(
        diaryRepository: DiaryRepository,
    ) = RemoveConsumedWaterUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideCreateMealUseCase(
        diaryRepository: DiaryRepository,
    ) = CreateMealUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideDeleteMealUseCase(
        diaryRepository: DiaryRepository,
    ) = DeleteMealUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideGetMealUseCase(
        diaryRepository: DiaryRepository,
    ) = GetMealByIdUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideGetMealsUseCase(
        diaryRepository: DiaryRepository,
    ) = GetMealsUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideLoadMealsUseCase(
        diaryRepository: DiaryRepository,
    ) = LoadMealsWithWaterUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideUpdateMealUseCase(
        diaryRepository: DiaryRepository,
    ) = UpdateMealUseCase(diaryRepository)

    @Provides
    @Singleton
    fun provideDiaryRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl,
    ): DiaryRepository = diaryRepositoryImpl

    @Provides
    @Singleton
    fun provideDiaryApiService(
        diaryApiServiceImpl: DiaryApiServiceImpl,
    ): DiaryApiService = diaryApiServiceImpl

}