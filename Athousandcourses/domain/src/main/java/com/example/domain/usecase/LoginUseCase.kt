package com.example.domain.usecase


import com.example.domain.model.AuthDataModel
import com.example.domain.model.AuthResultModel

import com.example.domain.model.ValidationResultModel
import com.example.domain.repositories.AuthRepository


class LoginUseCase(private val authRepository: AuthRepository) {
    fun execute(params:AuthDataModel,emailIsValid: Boolean): ValidationResultModel {
        if (params.email.isEmpty()) {
            return ValidationResultModel(false, AuthResultModel.EmptyEmail)
        }
        if (!emailIsValid) {
            return ValidationResultModel(false, AuthResultModel.ErrorEmail)
        }
        if (params.password.isEmpty()) {
            return ValidationResultModel(false, AuthResultModel.EmptyPassword)
        }
        authRepository.saveAuth(true)

        return ValidationResultModel(true, AuthResultModel.Success)
    }

}