package com.example.wakemeup

import com.example.wakemeup.RepositoryUtils.Companion.stringSuspending
import com.example.wakemeup.Result.Error
import com.example.wakemeup.Result.Success
import java.net.SocketTimeoutException
import javax.inject.Inject

class BuddiesRepository @Inject constructor(private val buddiesService: BuddiesService) {

  suspend fun getBuddies(): Result<List<String>> =
    try {
      buddiesService.getBuddies().run {
        when {
          isSuccessful && body() != null -> {
            Success(body()!!)
          }
          else -> {
            val errorMessage = errorBody()?.stringSuspending()
            Error(Exception(errorMessage))
          }
        }
      }
    } catch (exception: SocketTimeoutException) {
      Error(Exception("Error fetching group details."))
    }
}