package com.example.domain.model

enum class AuthResultModel(val displayText: String) {
    EmptyEmail("Введите email"),
    ErrorEmail("Некорректный email"),
    EmptyPassword("Введите пароль"),
    Success("Успешная проверка")
}