package com.example.athousandcourses.presentation.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.GetAuthUseCase
import com.example.domain.usecase.LoginUseCase

class LoginViewModelFactory(val getUserUSeCase: GetAuthUseCase, val loginUSeCase: LoginUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            loginUseCase = loginUSeCase,
            getAuthUseCase = getUserUSeCase

        ) as T
    }
}