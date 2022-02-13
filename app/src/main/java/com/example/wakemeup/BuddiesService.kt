package com.example.wakemeup

import retrofit2.Response
import retrofit2.http.GET

interface BuddiesService {

  @GET
  suspend fun getBuddies(): Response<List<String>>
}