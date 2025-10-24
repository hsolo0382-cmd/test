package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CourseDb::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract  fun courseDao(): CourseDao
}



