package com.challenge.compass.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CompassApiService {
    @GET("about/")
    @Headers("Accept: text/plain")
    suspend fun getAbout(): Response<String>
}