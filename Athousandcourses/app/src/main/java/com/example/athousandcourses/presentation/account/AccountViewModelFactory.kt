package com.example.athousandcourses.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.UnLoginUseCase

class AccountViewModelFactory(val unLoginUseCase: UnLoginUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AccountViewModel(
            unLoginUseCase = unLoginUseCase,
        ) as T
    }
}