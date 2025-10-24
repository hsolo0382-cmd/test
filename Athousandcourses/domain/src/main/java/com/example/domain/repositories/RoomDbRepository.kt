package com.example.domain.repositories

import com.example.domain.model.CourseModel

interface RoomDbRepository {
    suspend fun save(course: CourseModel)
    suspend fun getAll():List<CourseModel>
    suspend fun delete(course: CourseModel)
}