package com.example.athousandcourses.presentation.courseinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.SaveCourseDataUseCase

class CourseInfoViewModelFactory  (
val saveCourseDataUseCase:SaveCourseDataUseCase,
val deleteCourseInDbUseCase:DeleteCourseInDbUseCase,

): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CourseInfoViewModel(
            saveFavoriteUseCase = saveCourseDataUseCase,
            deleteCourseInDbUseCase=deleteCourseInDbUseCase,

        ) as T
    }
}