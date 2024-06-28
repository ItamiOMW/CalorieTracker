package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.repository.UserRepository
import com.itami.calorie_tracker.core.utils.AppResponse

class DeleteAccountUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): AppResponse<Unit> {
        return userRepository.deleteUser()
    }

}