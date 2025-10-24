package com.example.athousandcourses.presentation.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athousandcourses.R
import com.example.athousandcourses.app.App
import com.example.athousandcourses.databinding.FragmentFavoriteBinding
import com.example.athousandcourses.presentation.home.adapter.CourseAdapter
import com.example.athousandcourses.utils.ArgsKeys
import com.google.gson.Gson
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    //vможно использовать и homeviewmodel
    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var vmFactory: FavoriteViewModelFactory
    private val courseAdapter = CourseAdapter { it -> viewModel.onItemClick(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(
                this,
                vmFactory
            )[FavoriteViewModel::class.java]
        binding.favoriteVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        observeFavoriteCourses()
        observeNavigationFlow()
    }


    private fun setupRecyclerView() {
        binding.favoriteRecycler.adapter = courseAdapter
        binding.favoriteRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun observeFavoriteCourses() {
        viewModel.favoriteCoursesFlow
            .onEach { courses ->
                courseAdapter.setItems(courses)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeNavigationFlow() {
        viewModel.navigationSharedFlow
            .filterNotNull()
            .onEach { item ->
                val bundle = Bundle().apply {
                    putString(ArgsKeys.TRANSFER_SELECTED_COURSE, Gson().toJson(item))
                }
                findNavController().navigate(R.id.action_favoriteFragment_to_courseInfoFragment, bundle)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
    viewModel.refresh()
    }
    override fun onDestroy() {
        super.onDestroy()
    _binding=null
    }
}