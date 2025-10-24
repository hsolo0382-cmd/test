package com.example.data.network.getcoursesdata

import okhttp3.ResponseBody
import retrofit2.http.GET


interface ApiService {
    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&e=export=download/")
    suspend fun downloadCourses(): ResponseBody
}