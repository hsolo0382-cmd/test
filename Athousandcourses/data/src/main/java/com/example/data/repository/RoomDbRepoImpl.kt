package com.example.data.repository

import com.example.data.local.db.CourseDao
import com.example.data.local.db.CourseDb
import com.example.domain.model.CourseModel
import com.example.domain.repositories.RoomDbRepository

class RoomDbRepoImpl(private val courseDao: CourseDao) : RoomDbRepository {
    override suspend fun save(course: CourseModel) {
        courseDao.insert(
            CourseDb(
                course.id,
                course.title,
                course.text,
                course.price,
                course.rate,
                course.startDate,
                course.hasLike,
                course.publishDate
            )
        )
    }

    override suspend fun getAll(): List<CourseModel> {
        return courseDao.getAll().map { course ->
            CourseModel(
                course.id,
                course.title,
                course.text,
                course.price,
                course.rate,
                course.startDate,
                course.hasLike,
                course.publishDate,
                null
            )
        }
    }

    override suspend fun delete(course: CourseModel) {
        val dataCourse = CourseDb(
            course.id,
            course.title,
            course.text,
            course.price,
            course.rate,
            course.startDate,
            course.hasLike,
            course.publishDate
        )

        courseDao.delete(dataCourse)
    }
}