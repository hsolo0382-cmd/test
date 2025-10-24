package com.example.data.network.getcoursesdata

import retrofit2.Retrofit

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .build()
            .create(ApiService::class.java)
    }
}