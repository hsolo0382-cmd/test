package com.example.athousandcourses.presentation.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CourseInteraction
import com.example.domain.model.CourseModel
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.GetCourseListFromDbUseCase
import com.example.domain.usecase.SaveCourseDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(
    private val saveFavoriteUseCase: SaveCourseDataUseCase,
    private val deleteCourseInDbUseCase: DeleteCourseInDbUseCase,
    private val getCourseListFromDbUseCase: GetCourseListFromDbUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _favorite_courses = MutableStateFlow<List<CourseModel>>(emptyList())
    val favoriteCoursesFlow = _favorite_courses.asStateFlow()
    private val _error = MutableStateFlow(false)
    val error = _error.asStateFlow()
    private val _navigationSharedFlow = MutableSharedFlow<CourseModel?>()
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    init {
        getFavoriteCourses()
    }

    private fun getFavoriteCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = false
            try {
                val result = withContext(Dispatchers.IO) {
                    getCourseListFromDbUseCase.execute()
                }
                _favorite_courses.value = result
                if (result.isEmpty()) _error.value = true

            } catch (e: Exception) {
                Log.d("coursesFavoriteLoadError", e.message ?: "")
            } finally {
                _isLoading.value = false
            }

        }
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
                _favorite_courses.update { currentList ->
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

    private fun saveCourseInDb(updatedCourse: CourseModel) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if (updatedCourse.hasLike) {
                    saveFavoriteUseCase.execute(updatedCourse)
                } else {
                    deleteCourseInDbUseCase.execute(updatedCourse)
                }
            }
        }
    }

    fun refresh() {
        getFavoriteCourses()
    }
}