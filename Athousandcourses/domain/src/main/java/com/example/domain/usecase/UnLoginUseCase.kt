package com.example.domain.usecase

import com.example.domain.repositories.AuthRepository

class UnLoginUseCase(private val authRepository: AuthRepository) {

    fun execute(): Boolean{
        authRepository.saveAuth(false)
        return true
    }
}