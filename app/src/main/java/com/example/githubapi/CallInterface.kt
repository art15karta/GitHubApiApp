package com.example.githubapi

import retrofit2.http.GET
import retrofit2.http.Path

interface CallInterface {

    @GET("users/{name}")
    suspend fun getUsersData(@Path("name") name: String): DataUser
}