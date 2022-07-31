package com.trip.livedatanosticky

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<Int?>()
    val data: LiveData<Int?> = _data

    fun increment() {
        val value = _data.value ?: -1
        _data.value = value + 1
    }
}