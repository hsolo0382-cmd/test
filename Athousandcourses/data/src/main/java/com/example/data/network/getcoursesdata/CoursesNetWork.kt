package com.example.data.network.getcoursesdata

import okhttp3.ResponseBody

class CoursesNetWork():ApiService {
     private val apiService = RetrofitInstance.api
    override  suspend fun downloadCourses():ResponseBody {
        val responseBody = apiService.downloadCourses()
        return responseBody
    }




}