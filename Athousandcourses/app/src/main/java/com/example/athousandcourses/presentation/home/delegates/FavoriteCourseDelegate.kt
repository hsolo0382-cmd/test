package com.example.athousandcourses.presentation.home.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athousandcourses.databinding.ItemFavoriteCourseBinding
import com.example.athousandcourses.utils.DateUtils
import com.example.domain.model.CourseInteraction
import com.example.domain.model.CourseModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class FavoriteCourseDelegate(
    private val onItemClick: (CourseInteraction) -> Unit
) : AdapterDelegate<List<CourseModel>>() {

    override fun isForViewType(items: List<CourseModel>, position: Int): Boolean {
        return items[position].hasLike
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemFavoriteCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteCourseViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<CourseModel>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) {
        val course = items[position]
        (holder as FavoriteCourseViewHolder).bind(course)
    }

    inner class FavoriteCourseViewHolder(private val binding: ItemFavoriteCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseModel) {

            binding.courseNameFavorite.text = course.title
            binding.courseDescriptionFavorite.text = course.text
            binding.coursePriceFavorite.text = course.price
            binding.ratingTextFavorite.text = course.rate
            binding.dateTextFavorite.text =   DateUtils.formatStartDate(course.publishDate)


            binding.moreInfoFavorite.setOnClickListener {
                onItemClick(CourseInteraction.MoreInfo(course))
            }
            binding.deleteInFavorite.setOnClickListener {
                onItemClick(CourseInteraction.Favorite(course))
            }
        }
    }
}