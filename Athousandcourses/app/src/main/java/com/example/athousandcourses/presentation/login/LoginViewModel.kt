package com.example.athousandcourses.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athousandcourses.R
import com.example.domain.model.AuthDataModel
import com.example.domain.model.AuthResultModel

import com.example.domain.usecase.GetAuthUseCase
import com.example.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val getAuthUseCase: GetAuthUseCase,
    ) : ViewModel() {
    private val _errorEmail = MutableLiveData<String?>()
    val errorEmail: LiveData<String?> = _errorEmail

    private val _errorPassword = MutableLiveData<String?>()
    val errorPassword: LiveData<String?> = _errorPassword

    private val _navigationSharedFlow = MutableSharedFlow<Int?>(1)
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()

    init {
        getAuth()
    }

    private fun getAuth() {
        val isAuthenticated = getAuthUseCase.execute()
        if (isAuthenticated) {
            viewModelScope.launch {
                _navigationSharedFlow.emit(R.id.action_loginFragment_to_homeFragment)
            }
        }

    }

    fun dataVerification(email: String, password: String) {

            val emailIsValid= Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val result = loginUseCase.execute(AuthDataModel(email, password),emailIsValid)
        // Обнуляем ошибки
        _errorEmail.value = null
        _errorPassword.value = null

        when (result.message) {
            AuthResultModel.EmptyEmail -> _errorEmail.value = result.message.displayText
            AuthResultModel.ErrorEmail -> _errorEmail.value = result.message.displayText
            AuthResultModel.EmptyPassword -> _errorPassword.value = result.message.displayText
            AuthResultModel.Success -> {
                viewModelScope.launch {
                    _navigationSharedFlow.emit(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }

    }


}