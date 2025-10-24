package com.example.athousandcourses.presentation.login

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.athousandcourses.app.App
import com.example.athousandcourses.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.example.athousandcourses.presentation.SharedViewModel
import kotlin.getValue


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    @Inject
    lateinit var vmFactory: LoginViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Внедрение зависимостей
        (requireContext().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]

        // Настройка UI
        setupEmailInputFilter()
        setupObservers()
        setupClickListeners()
        toggleBottomView(false)
    }

    private fun setupEmailInputFilter() {
        val rusLettersFilter = createRussianLettersFilter()
        binding.editTextEmail.filters = arrayOf(rusLettersFilter)
    }

    private fun createRussianLettersFilter() =
        InputFilter { source, start, end, dest, dstart, dend ->
            val pattern = Regex("[а-яА-ЯёЁ]")
            if (pattern.containsMatchIn(source.toString())) {
                "" // блокируем русские буквы
            } else {
                null // разрешаем остальное
            }
        }

    private fun setupObservers() {
        // Обработка ошибок email
        viewModel.errorEmail.observe(viewLifecycleOwner) { errorMsg ->
            binding.editTextEmail.error = errorMsg
        }
        // Обработка ошибок пароля
        viewModel.errorPassword.observe(viewLifecycleOwner) { errorMsg ->
            binding.editTextPssword.error = errorMsg
        }
        // Переход при успешной авторизации
        viewModel.navigationSharedFlow
            .filterNotNull()
            .onEach { destinationId ->
                findNavController().navigate(destinationId)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupClickListeners() {
        binding.login.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPssword.text.toString()
            viewModel.dataVerification(email, password)
        }

        setupExternalLinkButton(binding.buttonVk, "https://vk.com")
        setupExternalLinkButton(binding.buttonOk, "https://ok.ru")
    }

    private fun setupExternalLinkButton(button: View, url: String) {
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = url.toUri()
            }
            startActivity(intent)
        }
    }
    fun toggleBottomView(show: Boolean) {
        sharedViewModel.showBottomViewLiveData.value = show
    }
}