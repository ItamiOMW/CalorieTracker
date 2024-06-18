package com.itami.calorie_tracker.profile_feature.domain.use_case

import com.itami.calorie_tracker.core.domain.utils.ValidationUtil
import com.itami.calorie_tracker.core.utils.AppResponse
import com.itami.calorie_tracker.profile_feature.domain.repository.FeedbackRepository

class SendFeedbackUseCase(private val feedbackRepository: FeedbackRepository) {

    suspend operator fun invoke(message: String): AppResponse<Unit> {
        val trimmedMessage = message.trim()
        val messageException = ValidationUtil.validateFeedbackMessage(trimmedMessage)

        if (messageException != null) {
            return AppResponse.failed(messageException)
        }

        return feedbackRepository.sendFeedback(trimmedMessage)
    }

}