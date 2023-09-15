package com.example.appquanlycongviec

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkViewModel: ViewModel() {
    val currentWork = MutableLiveData<Work>()
}