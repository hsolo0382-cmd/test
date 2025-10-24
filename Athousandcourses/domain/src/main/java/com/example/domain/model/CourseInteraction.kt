package com.example.domain.model

sealed class CourseInteraction {
    data class MoreInfo(val course: CourseModel) : CourseInteraction()
    data class Favorite(val course: CourseModel) : CourseInteraction()
}