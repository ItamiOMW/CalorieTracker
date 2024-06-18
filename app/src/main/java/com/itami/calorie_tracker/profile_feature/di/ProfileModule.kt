package com.itami.calorie_tracker.profile_feature.di

import com.itami.calorie_tracker.profile_feature.data.remote.FeedbackApiService
import com.itami.calorie_tracker.profile_feature.data.remote.FeedbackApiServiceImpl
import com.itami.calorie_tracker.profile_feature.data.repository.FeedbackRepositoryImpl
import com.itami.calorie_tracker.profile_feature.domain.repository.FeedbackRepository
import com.itami.calorie_tracker.profile_feature.domain.use_case.SendFeedbackUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideFeedbackApiService(
        feedbackApiServiceImpl: FeedbackApiServiceImpl,
    ): FeedbackApiService = feedbackApiServiceImpl

    @Provides
    @Singleton
    fun provideFeedbackRepository(
        feedbackRepositoryImpl: FeedbackRepositoryImpl,
    ): FeedbackRepository = feedbackRepositoryImpl

    @Provides
    @Singleton
    fun provideSendFeedbackUseCase(
        feedbackRepository: FeedbackRepository,
    ) = SendFeedbackUseCase(feedbackRepository)

}