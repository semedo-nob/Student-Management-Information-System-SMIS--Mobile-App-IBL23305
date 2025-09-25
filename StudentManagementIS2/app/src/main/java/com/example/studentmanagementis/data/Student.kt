package com.example.studentmanagementis.data

// data/Student.kt


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val regNumber: String,
    val course: String,
    val dob: String? = null, // e.g., "1998-05-15"
    val gender: String? = null, // e.g., "Male"
    val address: String? = null, // e.g., "123 Main Street"
    val email: String? = null, // e.g., "ethan.carter@example.com"
    val phone: String? = null // e.g., "+1 (555) 123-4567"
)