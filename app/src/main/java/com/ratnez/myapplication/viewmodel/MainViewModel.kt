package com.ratnez.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratnez.myapplication.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var apiRepository: ApiRepository) : ViewModel() {

    suspend fun getPersonData() = apiRepository.getPersonData()

    fun updateUserAction(email: String, action: Int) {
        viewModelScope.launch {
            apiRepository.updateUserAction(email, action)
        }
    }
}