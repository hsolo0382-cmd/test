package com.example.data.repository

import com.example.data.network.getcoursesdata.ApiService
import com.example.data.network.getcoursesdata.CoursesResponse
import com.example.domain.model.CourseModel
import com.example.domain.repositories.GetCoursesRepository
import com.google.gson.Gson

class GetCourseRepoImpl(
    private val apiService: ApiService
):GetCoursesRepository {
    override suspend fun getCourses(): List<CourseModel> {
        val dataCourse =  apiService.downloadCourses()
        val jsonString = dataCourse.string()
        val coursesResponse = Gson().fromJson(jsonString, CoursesResponse::class.java)
        val domainCourse = mutableListOf<CourseModel>()
        coursesResponse.courses.forEach {
            domainCourse.add(CourseModel(it.id,it.title,it.text,it.price,it.rate,it.startDate,it.hasLike,it.publishDate,null))
        }
        return domainCourse
    }


}