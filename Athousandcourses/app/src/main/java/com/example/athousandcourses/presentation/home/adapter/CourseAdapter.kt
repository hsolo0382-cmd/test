package com.example.athousandcourses.presentation.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.athousandcourses.presentation.home.delegates.CourseAdapterDelegate
import com.example.athousandcourses.presentation.home.delegates.FavoriteCourseDelegate
import com.example.domain.model.CourseInteraction
import com.example.domain.model.CourseModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class CourseAdapter(
    private val onClick: (CourseInteraction) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CourseModel>()

    private val delegates = AdapterDelegatesManager<List<CourseModel>>()
        .addDelegate(CourseAdapterDelegate(onClick))
        .addDelegate(FavoriteCourseDelegate(onClick))
    fun setItems(newItems: List<CourseModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates.onBindViewHolder(items, position, holder, emptyList<CourseModel>())
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.getItemViewType(items, position)
    }
}
