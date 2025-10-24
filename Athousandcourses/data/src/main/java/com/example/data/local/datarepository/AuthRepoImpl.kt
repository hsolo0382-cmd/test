package com.example.data.local.datarepository


import com.example.data.local.saveauth.StorageInt
import com.example.domain.repositories.AuthRepository

class AuthRepoImpl(private val sAveAuthSharedPrefs: StorageInt): AuthRepository {


    override fun saveAuth(isLogin: Boolean) {
         sAveAuthSharedPrefs.save(isLogin)
    }

    override fun getAuth():Boolean {
        return sAveAuthSharedPrefs.get()
    }


}