// network/Result.kt
package com.example.studentmanagementis.network

data class Result(
    val studentId: Int,
    val subject: String,
    val score: Int,
    val grade: String
)