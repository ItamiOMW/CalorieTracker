package com.itami.calorie_tracker.core.domain.repository

import com.itami.calorie_tracker.core.domain.model.UpdateUser
import com.itami.calorie_tracker.core.utils.AppResponse

interface UserRepository {

    suspend fun updateUser(updateUser: UpdateUser): AppResponse<Unit>

}