package com.example.studentmanagementis.data

// data/AppDatabase.kt


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Student::class], version = 2) // Updated version for new fields
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studentmanagementis_db"
                )
                    .fallbackToDestructiveMigration() // For exam simplicity
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}