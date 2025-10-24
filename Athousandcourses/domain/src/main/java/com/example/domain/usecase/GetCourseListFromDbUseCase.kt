package com.example.domain.usecase

import com.example.domain.model.CourseModel
import com.example.domain.repositories.RoomDbRepository

class GetCourseListFromDbUseCase(private val roomDbRepository: RoomDbRepository) {
    suspend fun execute():List<CourseModel>{
        return roomDbRepository.getAll()
    }
}