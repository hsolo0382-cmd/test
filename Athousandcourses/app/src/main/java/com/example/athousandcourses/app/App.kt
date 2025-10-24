package com.example.athousandcourses.app

import android.app.Application
import com.example.athousandcourses.di.AppComponent
import com.example.athousandcourses.di.AppModule
import com.example.athousandcourses.di.DaggerAppComponent

class App:Application() {
lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent= DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }
}