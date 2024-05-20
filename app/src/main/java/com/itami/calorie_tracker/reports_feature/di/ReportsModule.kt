package com.itami.calorie_tracker.reports_feature.di

import com.itami.calorie_tracker.reports_feature.data.remote.ReportsApiService
import com.itami.calorie_tracker.reports_feature.data.remote.ReportsApiServiceImpl
import com.itami.calorie_tracker.reports_feature.data.repository.ReportsRepositoryImpl
import com.itami.calorie_tracker.reports_feature.domain.repository.ReportsRepository
import com.itami.calorie_tracker.reports_feature.domain.use_case.AddWeightUseCase
import com.itami.calorie_tracker.reports_feature.domain.use_case.EditWeightUseCase
import com.itami.calorie_tracker.reports_feature.domain.use_case.GetWeightsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReportsModule {

    @Provides
    @Singleton
    fun provideGetWeightsUseCase(
        reportsRepository: ReportsRepository
    ) = GetWeightsUseCase(reportsRepository)

    @Provides
    @Singleton
    fun provideAddWeightUseCase(
        reportsRepository: ReportsRepository
    ) = AddWeightUseCase(reportsRepository)

    @Provides
    @Singleton
    fun provideEditWeightUseCase(
        reportsRepository: ReportsRepository
    ) = EditWeightUseCase(reportsRepository)

    @Provides
    @Singleton
    fun provideReportsApiService(
        reportsApiServiceImpl: ReportsApiServiceImpl
    ): ReportsApiService = reportsApiServiceImpl

    @Provides
    @Singleton
    fun provideReportsRepository(
        reportsRepositoryImpl: ReportsRepositoryImpl
    ): ReportsRepository = reportsRepositoryImpl

}