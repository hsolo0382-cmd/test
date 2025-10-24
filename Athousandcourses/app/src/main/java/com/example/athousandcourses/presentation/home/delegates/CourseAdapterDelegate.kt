package com.example.athousandcourses.presentation.home.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athousandcourses.databinding.ItemCourseBinding
import com.example.athousandcourses.utils.DateUtils
import com.example.domain.model.CourseInteraction
import com.example.domain.model.CourseModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class CourseAdapterDelegate(
    private val onItemClick: (CourseInteraction) -> Unit
) : AdapterDelegate<List<CourseModel>>() {



    override fun isForViewType(items: List<CourseModel>, position: Int): Boolean {
        return !items[position].hasLike
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(items: List<CourseModel>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        val course = items[position]
        (holder as CourseViewHolder).bind(course)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(course: CourseModel) {


            binding.courseName.text = course.title
            binding.courseDescription.text = course.text
            binding.coursePrice.text = course.price
            binding.ratingText.text = course.rate
            binding.dateText.text = DateUtils.formatStartDate(course.publishDate)

            binding.moreInfo.setOnClickListener {
                onItemClick(CourseInteraction.MoreInfo(course))
            }
            binding.saveInFavorite.setOnClickListener {
                onItemClick(CourseInteraction.Favorite(course))
            }
        }
    }
}