package com.itami.calorie_tracker.core.domain.exceptions

sealed class FeedbackMessageException: AppException() {

    data object EmptyMessageException: FeedbackMessageException()

    data object ShortMessageException: FeedbackMessageException()

    data object LargeMessageException: FeedbackMessageException()

}