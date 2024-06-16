package com.itami.calorie_tracker.core.domain.use_case

import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.domain.model.UpdateUserResult
import com.itami.calorie_tracker.core.domain.repository.UserRepository
import com.itami.calorie_tracker.core.domain.utils.ValidationUtil

class UpdateUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(updateUser: UpdateUser): UpdateUserResult {
        val ageException = updateUser.age?.let { ValidationUtil.validateAge(it) }
        val weightException = updateUser.weightGrams?.let { ValidationUtil.validateWeight(it) }
        val heightException = updateUser.heightCm?.let { ValidationUtil.validateHeight(it) }

        if (ageException != null || weightException != null || heightException != null) {
            return UpdateUserResult(
                ageException = ageException,
                weightException = weightException,
                heightException = heightException,
            )
        }

        val response = userRepository.updateUser(updateUser = updateUser)
        return UpdateUserResult(response = response)
    }

}
