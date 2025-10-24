package com.example.athousandcourses.di

import android.content.Context
import com.example.athousandcourses.presentation.account.AccountViewModelFactory
import com.example.athousandcourses.presentation.courseinfo.CourseInfoViewModelFactory
import com.example.athousandcourses.presentation.favorite.FavoriteViewModelFactory
import com.example.athousandcourses.presentation.home.HomeViewModelFactory
import com.example.athousandcourses.presentation.login.LoginViewModelFactory
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
class AppModule(val context: Context) {
    @Provides
    fun provideContext( ):Context{
        return context
    }
    @Provides
    fun provideLoginViewModelFactory( getUserUSeCase: GetAuthUseCase,  loginUSeCase: LoginUseCase):LoginViewModelFactory{
        return LoginViewModelFactory(getUserUSeCase,loginUSeCase)
    }
    @Provides
    fun provideHomeViewModelFactory(getCoursesUseCase: GetCourseUseCase,
                                    saveFavoriteUseCase : SaveCourseDataUseCase,
                                    deleteCourseInDbUseCase: DeleteCourseInDbUseCase,
                                    getCourseListFromDbUseCase: GetCourseListFromDbUseCase
    ): HomeViewModelFactory{
        return HomeViewModelFactory(
            getCourseUseCase = getCoursesUseCase,
            saveCourseDataUseCase = saveFavoriteUseCase,
            deleteCourseInDbUseCase=deleteCourseInDbUseCase,
            getCourseListFromDbUseCase = getCourseListFromDbUseCase)
    }
    @Provides
    fun provideCourseInfoViewModelFactory( saveCourseDataUseCase:SaveCourseDataUseCase,
                                           deleteCourseInDbUseCase:DeleteCourseInDbUseCase): CourseInfoViewModelFactory{
        return CourseInfoViewModelFactory(saveCourseDataUseCase=saveCourseDataUseCase,deleteCourseInDbUseCase=deleteCourseInDbUseCase)
    }

    @Provides
    fun provideFavoriteViewModelFactory( saveCourseDataUseCase:SaveCourseDataUseCase,
                                           deleteCourseInDbUseCase:DeleteCourseInDbUseCase,
                                         getCourseListFromDbUseCase: GetCourseListFromDbUseCase): FavoriteViewModelFactory{
        return FavoriteViewModelFactory(saveCourseDataUseCase=saveCourseDataUseCase,deleteCourseInDbUseCase=deleteCourseInDbUseCase,getCourseListFromDbUseCase=getCourseListFromDbUseCase)
    }

    @Provides
    fun provideAccountViewModelFactory( unLoginUseCase: UnLoginUseCase ): AccountViewModelFactory{
        return AccountViewModelFactory(unLoginUseCase=unLoginUseCase)
    }

}