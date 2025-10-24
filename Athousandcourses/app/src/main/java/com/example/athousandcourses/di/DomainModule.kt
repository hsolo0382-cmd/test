package com.example.athousandcourses.di


import com.example.domain.repositories.AuthRepository
import com.example.domain.repositories.GetCoursesRepository
import com.example.domain.repositories.RoomDbRepository
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.GetAuthUseCase
import com.example.domain.usecase.GetCourseListFromDbUseCase
import com.example.domain.usecase.GetCourseUseCase
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.SaveCourseDataUseCase
import com.example.domain.usecase.UnLoginUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    //login fragment
    @Provides
    fun provideGetAuthUseCase(authRepository: AuthRepository): GetAuthUseCase {
        return GetAuthUseCase(authRepository)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
    //home fragment

    @Provides
    fun provideGetCourseUseCase(repository: GetCoursesRepository): GetCourseUseCase {
        return GetCourseUseCase(repository)
    }

    @Provides
    fun provideSaveCourseDataUseCase(roomDbRepository: RoomDbRepository):SaveCourseDataUseCase{
        return SaveCourseDataUseCase(roomDbRepository)
    }

    @Provides
    fun provideDeleteCourseInDbUseCase(roomDbRepository: RoomDbRepository):DeleteCourseInDbUseCase{
        return DeleteCourseInDbUseCase(roomDbRepository)
    }
    @Provides
    fun provideGetCourseListFromDbUseCase(roomDbRepository: RoomDbRepository):GetCourseListFromDbUseCase{
        return GetCourseListFromDbUseCase(roomDbRepository)
    }
    @Provides
    fun provideUnLoginUseCase(authRepository: AuthRepository):UnLoginUseCase{
        return UnLoginUseCase(authRepository)
    }
}