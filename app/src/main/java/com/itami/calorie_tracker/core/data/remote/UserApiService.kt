package com.itami.calorie_tracker.core.data.remote

import com.itami.calorie_tracker.core.data.remote.request.ChangePasswordRequest
import com.itami.calorie_tracker.core.data.remote.request.UpdateUserRequest
import com.itami.calorie_tracker.core.data.remote.response.ApiResponse
import com.itami.calorie_tracker.core.data.remote.response.ErrorResponse
import com.itami.calorie_tracker.core.data.remote.response.UserResponse

interface UserApiService {

    suspend fun updateUser(
        token: String,
        updateUserRequest: UpdateUserRequest,
        profileImageByteArray: ByteArray?,
    ): ApiResponse<UserResponse, ErrorResponse>

    suspend fun deleteAccount(
        token: String
    ): ApiResponse<Unit, ErrorResponse>

    suspend fun changePassword(
        token: String,
        changePasswordRequest: ChangePasswordRequest
    ): ApiResponse<Unit, ErrorResponse>

}