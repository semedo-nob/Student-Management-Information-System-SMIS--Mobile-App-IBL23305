// network/ApiService.kt
package com.example.studentmanagementis.network

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("students/results")
    suspend fun getResults(): List<Result>

    @DELETE("students/{id}")
    suspend fun deleteStudent(@Path("id") id: Int)
}