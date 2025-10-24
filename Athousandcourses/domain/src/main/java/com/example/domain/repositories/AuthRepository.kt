package com.example.domain.repositories



interface AuthRepository{
    fun saveAuth(isLogin:Boolean)
    fun getAuth():Boolean
}