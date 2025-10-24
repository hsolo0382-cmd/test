package com.example.athousandcourses.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val showBottomViewLiveData = MutableLiveData<Boolean>()
}