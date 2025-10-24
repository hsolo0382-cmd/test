package com.example.athousandcourses.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.GetCourseListFromDbUseCase
import com.example.domain.usecase.GetCourseUseCase
import com.example.domain.usecase.SaveCourseDataUseCase

class HomeViewModelFactory(
    val getCourseUseCase: GetCourseUseCase,
    val saveCourseDataUseCase:SaveCourseDataUseCase,
    val deleteCourseInDbUseCase:DeleteCourseInDbUseCase,
    val getCourseListFromDbUseCase:GetCourseListFromDbUseCase

    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
           getCoursesUseCase = getCourseUseCase,
            saveFavoriteUseCase = saveCourseDataUseCase,
            deleteCourseInDbUseCase=deleteCourseInDbUseCase,
            getCourseListFromDbUseCase = getCourseListFromDbUseCase

            ) as T
    }
}