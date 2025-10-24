package com.example.athousandcourses.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.athousandcourses.R
import com.example.domain.usecase.UnLoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val unLoginUseCase: UnLoginUseCase,
) : ViewModel() {
    private val _navigationSharedFlow = MutableSharedFlow<Int?>(1)
    val navigationSharedFlow = _navigationSharedFlow.asSharedFlow()
    fun unLogin() {
        unLoginUseCase.execute()
        viewModelScope.launch {
            _navigationSharedFlow.emit(R.id.action_accountFragment_to_loginFragment)
        }
    }

    }