package com.example.domain.usecase

import com.example.domain.repositories.AuthRepository


class GetAuthUseCase(private val authRepository: AuthRepository) {
 fun execute():Boolean{
    return authRepository.getAuth()

}
}