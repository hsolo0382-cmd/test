package com.example.domain.usecase

import com.example.domain.model.CourseModel
import com.example.domain.repositories.GetCoursesRepository

class GetCourseUseCase(private val getCoursesRepository: GetCoursesRepository) {
    suspend fun execute(): List<CourseModel> {
        return getCoursesRepository.getCourses()
    }
}