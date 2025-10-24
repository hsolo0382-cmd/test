package com.example.athousandcourses.presentation.courseinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.CourseModel
import com.example.domain.usecase.DeleteCourseInDbUseCase
import com.example.domain.usecase.SaveCourseDataUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CourseInfoViewModel(
    private val saveFavoriteUseCase: SaveCourseDataUseCase,
    private val deleteCourseInDbUseCase: DeleteCourseInDbUseCase,
) : ViewModel() {


    private val _course = MutableLiveData<CourseModel>()
    val course: LiveData<CourseModel> = _course

     fun saveCourseInDb() {
         viewModelScope.launch {
             val currentCourse = _course.value
             if (currentCourse != null) {
                 val newHasLike = !currentCourse.hasLike
                 val updatedCourse = currentCourse.copy(hasLike = newHasLike)

                 // Обновляем LiveData на главном потоке так как обновляем ui
                 withContext(Dispatchers.Main) {
                     _course.value = updatedCourse
                 }

                 try {
                     if (updatedCourse.hasLike) {
                         // сохраняем в избранное
                         saveFavoriteUseCase.execute(updatedCourse)
                     } else {
                         // удаляем из избранных
                         deleteCourseInDbUseCase.execute(updatedCourse)
                     }
                 } catch (e: Exception) {

                     Log.d("coursesLoadError", e.message ?: "")
                 }
             }
         }
     }

    fun setCourseJson(argsJson: String?) {

        argsJson?.let {
                val courseModel = Gson().fromJson(it, CourseModel::class.java)
                _course.value = courseModel

        }
    }
}