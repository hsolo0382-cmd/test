package com.example.domain.usecase

import com.example.domain.model.CourseModel
import com.example.domain.repositories.RoomDbRepository

class DeleteCourseInDbUseCase(private val roomDbRepository: RoomDbRepository) {
    suspend fun execute(course: CourseModel){
        roomDbRepository.delete(course)
    }
}