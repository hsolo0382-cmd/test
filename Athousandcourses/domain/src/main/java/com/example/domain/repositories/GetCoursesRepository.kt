package com.example.domain.repositories

import com.example.domain.model.CourseModel

interface GetCoursesRepository {
    suspend fun getCourses():List<CourseModel>
}