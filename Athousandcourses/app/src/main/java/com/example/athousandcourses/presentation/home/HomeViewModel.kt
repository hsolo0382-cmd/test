package com.example.athousandcourses.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CourseInteraction
import com.example.domain.model.CourseModel
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.GetCourseListFromDbUseCase
import com.example.domain.usecase.GetCourseUseCase
import com.example.domain.usecase.SaveCourseDataUseCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class HomeViewModel(
    private val getCoursesUseCase: GetCourseUseCase,
    private val saveFavoriteUseCase: SaveCourseDataUseCase,
    private val deleteCourseInDbUseCase: DeleteCourseInDbUseCase,
    private val getCourseListFromDbUseCase: GetCourseListFromDbUseCase
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _courses = MutableStateFlow<List<CourseModel>>(emptyList())
    val courses = _courses.asStateFlow()
    private val _favorite_courses = MutableStateFlow<List<CourseModel>>(emptyList())

    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()
    private val _navigationSharedFlow = MutableSharedFlow<CourseModel?>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()
    var isSorted = false

    init {

        loadCourses()
        getFavoriteCourses()
    }
    fun getFavoriteCourses(){
        viewModelScope.launch {
            _error.value = false
            try {
                val result = withContext(Dispatchers.IO) {
                    getCourseListFromDbUseCase.execute()
                }
                _favorite_courses.value = result
            } catch (e: Exception) {
                Log.d("coursesFavoriteLoadError", e.message ?: "")
            }

        }
    }
    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = false
            try {
                //поток для операций
                val result = withContext(Dispatchers.IO) {
                getCoursesUseCase.execute()
                }
                _courses.value = result
                refreshCourses()
            } catch (e: Exception) {
                _error.value = true
                Log.d("coursesLoadError", e.message ?: "")
            } finally {
                _isLoading.value = false
            }


        }

    }
    fun refreshCourses() {
        // Получение списков (пример)
        val allCourses = _courses.value
        val favoriteCourses = _favorite_courses.value

        val favoriteIds = favoriteCourses.map { it.id }.toSet()

        val updatedCourses = allCourses.map { course ->
            if (favoriteIds.contains(course.id)) {
                val favCourse = favoriteCourses.first { it.id == course.id }
                course.copy(hasLike = favCourse.hasLike)
            } else {
                course
            }
        }

        _courses.value = updatedCourses
    }
    fun onItemClick(it: CourseInteraction) {
        when (it) {
            is CourseInteraction.MoreInfo -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(it.course)
                }
            }

            is CourseInteraction.Favorite -> {

                val course = it.course
                val newHasLike = !course.hasLike
                val updatedCourse = course.copy(hasLike = newHasLike)
                _courses.update { currentList ->
                    currentList.map { item ->
                        if (item.id == course.id) {
                            saveCourseInDb(updatedCourse)
                            updatedCourse

                        } else {
                            item
                        }
                    }
                }

            }
        }
    }

    fun sorting() {
        isSorted = !isSorted
        if (isSorted) {
            _courses.value = _courses.value.sortedBy { LocalDate.parse(it.publishDate) }
        } else {
            _courses.value = _courses.value.sortedByDescending { LocalDate.parse(it.publishDate) }
        }
    }


    private fun saveCourseInDb(updatedCourse: CourseModel) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    if (updatedCourse.hasLike) {
                        saveFavoriteUseCase.execute(updatedCourse)
                    }else {
                        deleteCourseInDbUseCase.execute(updatedCourse)
                    }
                }

            }
        }

    fun refresh() {
        loadCourses()
        getFavoriteCourses()
    }
}

