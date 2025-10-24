package com.example.data.local.saveauth

interface StorageInt {
    fun save(saveParam: Boolean): Boolean
    fun get(): Boolean
}