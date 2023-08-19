package com.example.userlistwithkoin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlistwithkoin.UserRepository
import com.example.userlistwithkoin.model.Data
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Progress)
    val state = _state

    init {
        getUser()
    }
    private fun getUser() = viewModelScope.launch {
        _state.value =  UserState.Success(repository.getUser())
    }
}

sealed class UserState{
    data object Progress : UserState()
    class Success(val list: ArrayList<Data>) : UserState()
}