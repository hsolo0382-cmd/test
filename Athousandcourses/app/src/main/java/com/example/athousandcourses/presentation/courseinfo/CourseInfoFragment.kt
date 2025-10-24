package com.example.athousandcourses.presentation.courseinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.athousandcourses.R
import com.example.athousandcourses.app.App
import com.example.athousandcourses.databinding.FragmentCourseInfoBinding

import com.example.athousandcourses.utils.ArgsKeys
import com.example.athousandcourses.utils.DateUtils
import com.example.domain.model.CourseModel
import javax.inject.Inject

class CourseInfoFragment : Fragment() {

    private var _binding: FragmentCourseInfoBinding? = null
    val binding get()=_binding!!
    private lateinit var viewModel: CourseInfoViewModel
    @Inject
    lateinit var vmFactory: CourseInfoViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
         _binding = FragmentCourseInfoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(
                this,
                vmFactory
            )[CourseInfoViewModel::class.java]

        initArguments()
        observeCourse()
        setupClickListeners()

    }
    private fun initArguments() {
        val argsJson = arguments?.getString(ArgsKeys.TRANSFER_SELECTED_COURSE)
        viewModel.setCourseJson(argsJson)
    }

    private fun observeCourse() {
        viewModel.course.observe(viewLifecycleOwner) { course ->
            course?.let { updateUI(it) }
        }
    }

    private fun updateUI(course: CourseModel) {
        binding.apply {
            binding.courseName
            dateInCourseInfo.text = DateUtils.formatStartDate(course.publishDate)
            rateInCourseInfo.text = course.rate
            courseName.text = course.title
            descriptionCourseInfo.text = course.text

            if (course.hasLike) {
                favoriteBtn.setImageResource(R.drawable.selected_bookmark)
            } else {
                favoriteBtn.setImageResource(R.drawable.black_bookmark)
            }
        }
    }

    private fun setupClickListeners() {
        binding.favoriteBtn.setOnClickListener {
            viewModel.saveCourseInDb()
        }
        binding.backFromCourseInfo.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    _binding = null
    }
}