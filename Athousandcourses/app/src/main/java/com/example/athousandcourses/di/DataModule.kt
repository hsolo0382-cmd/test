package com.example.athousandcourses.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.datarepository.AuthRepoImpl
import com.example.data.local.db.AppDatabase
import com.example.data.local.saveauth.SAveAuthSharedPrefs
import com.example.data.local.saveauth.StorageInt
import com.example.data.network.getcoursesdata.ApiService
import com.example.data.network.getcoursesdata.CoursesNetWork
import com.example.data.repository.GetCourseRepoImpl
import com.example.data.repository.RoomDbRepoImpl
import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.GetCoursesRepository
import com.example.domain.repositories.RoomDbRepository

import dagger.Module
import dagger.Provides

@Module
class DataModule {
    //login fragment
    @Provides
    fun provideSAveAuthSharedPrefs(context: Context): StorageInt {
        return SAveAuthSharedPrefs(context)
    }

    @Provides
    fun provideauthRepository(storageInt: StorageInt): AuthRepository {
        return AuthRepoImpl(storageInt)
    }

    //homefragment

    @Provides
    fun provideApiService():ApiService{
        return CoursesNetWork()
    }
    @Provides
    fun provideGetCoursesRepository(apiService:ApiService):GetCoursesRepository{
        return GetCourseRepoImpl(apiService)
    }
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db" // Название базы данных
        ).build()
    }

    @Provides
    fun provideRoomDbRepository(appDatabase: AppDatabase):RoomDbRepository{
        return RoomDbRepoImpl(appDatabase.courseDao())
    }



}