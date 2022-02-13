package com.example.wakemeup

sealed class Result<out T : Any> {

  data class Success<out T : Any>(val data: T) : Result<T>()
  data class Created<out T: Any>(val data: T) : Result<T>()
  data class Error(val exception: Exception) : Result<Nothing>()

  override fun toString(): String {
    return when (this) {
      is Success<*> -> "Success[data=$data]"
      is Created<*> -> "Created[data=$data]"
      is Error -> "Error[exception=$exception]"
    }
  }
}