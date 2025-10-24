package com.example.athousandcourses.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.GetCourseListFromDbUseCase
import com.example.domain.usecase.SaveCourseDataUseCase

class FavoriteViewModelFactory(
    val deleteCourseInDbUseCase:DeleteCourseInDbUseCase,
    val saveCourseDataUseCase:SaveCourseDataUseCase,
    val getCourseListFromDbUseCase: GetCourseListFromDbUseCase,

    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            saveFavoriteUseCase = saveCourseDataUseCase,
            deleteCourseInDbUseCase=deleteCourseInDbUseCase,
            getCourseListFromDbUseCase=getCourseListFromDbUseCase

            ) as T
    }
}