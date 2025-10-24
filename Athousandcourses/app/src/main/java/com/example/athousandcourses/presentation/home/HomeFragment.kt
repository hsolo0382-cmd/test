package com.example.athousandcourses.presentation.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.athousandcourses.R
import com.example.athousandcourses.app.App
import com.example.athousandcourses.databinding.FragmentHomeBinding
import com.example.athousandcourses.presentation.SharedViewModel
import com.example.athousandcourses.presentation.home.adapter.CourseAdapter
import com.example.athousandcourses.utils.ArgsKeys
import com.google.gson.Gson
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var viewModel: HomeViewModel
    @Inject
    lateinit var vmFactory: HomeViewModelFactory
    private val courseAdapter = CourseAdapter { it -> viewModel.onItemClick(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Внедрение зависимостей
        (requireContext().applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(
                this,
                vmFactory
            )[HomeViewModel::class.java]
                //настраивавем дата биндинг для отображения загрузки и ошибок
        binding.homeVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
            //отображение нижней панели
        toggleBottomView(true)
        // настройка RecyclerView
        setupRecyclerView()

        // подписка на данные из ViewModel
        subscribeToViewModel()

        // настройка  сортировки
        setupClickListeners()
    }




    private fun setupRecyclerView() {
        binding.recycler.adapter = courseAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun subscribeToViewModel() {
        viewModel.courses.onEach { courses ->
            courseAdapter.setItems(courses)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        // Подписка на навигационные события
        viewModel.navigationSharedFlow
            .filterNotNull()
            .onEach { item ->
                // можно и через Safe Args
                val bundle = Bundle().apply {
                    putString(ArgsKeys.TRANSFER_SELECTED_COURSE, Gson().toJson(item))
                }
                findNavController().navigate(R.id.action_homeFragment_to_courseInfoFragment, bundle)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupClickListeners() {
        // Обработка клика по иконке сортировки
        binding.sortingImg.setOnClickListener {
            viewModel.sorting()
        }
    }


    fun toggleBottomView(show: Boolean) {
        sharedViewModel.showBottomViewLiveData.value = show
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}