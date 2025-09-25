package com.example.studentmanagementis.data

// data/StudentDao.kt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface StudentDao {
    @Insert
    suspend fun insert(student: Student)

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<Student>

    @Delete
    suspend fun delete(student: Student)
}