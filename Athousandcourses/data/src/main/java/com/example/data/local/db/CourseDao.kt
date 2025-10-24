package com.example.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
 interface CourseDao {
    @Query("SELECT * FROM course")
    fun getAll(): List<CourseDb>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(course: CourseDb)

    @Delete
    suspend fun delete(name: CourseDb)

    @Update
    suspend fun update(name: CourseDb)

}