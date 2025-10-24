package com.example.athousandcourses.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.athousandcourses.R
import com.example.athousandcourses.app.App
import com.example.athousandcourses.databinding.FragmentAccountBinding
import com.example.athousandcourses.presentation.customview.AccountCustomView.OnButtonAccountViewClickListener
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AccountViewModel
    @Inject
    lateinit var vmFactory: AccountViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
      _binding = FragmentAccountBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(
                this,
                vmFactory
            )[AccountViewModel::class.java]
        viewModel.navigationSharedFlow
            .filterNotNull()
            .onEach { destinationId ->

                findNavController().navigate(
                    destinationId, null, NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true)
                        .setLaunchSingleTop(true)
                        .setRestoreState(false)
                    .build())

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        binding.exit.setButtonClickListener (object : OnButtonAccountViewClickListener{
            override fun onButtonClick() {
                viewModel.unLogin()
            }

        }

        )

    }

    override fun onDestroy() {
        super.onDestroy()
    _binding = null
    }
}