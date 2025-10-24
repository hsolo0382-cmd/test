package com.example.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "course")
data class CourseDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "rate")
    val rate: String,
    @ColumnInfo(name = "startDate")
    val startDate: String,
    @ColumnInfo(name = "hasLike")
    val hasLike: Boolean,
    @ColumnInfo(name = "publishDate")
    val publishDate: String
) {
}