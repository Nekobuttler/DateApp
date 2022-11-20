package com.example.dateappproject.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.dateappproject.Repository.UserRepository
import com.example.dateappproject.data.UsersDao
import com.example.dateappproject.model.Users
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository = UserRepository(UsersDao())

    val getUsers = repository.getAllData

    fun saveUser(user: Users) {
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    /*
    suspend fun updatePlace(place: Place) {
        viewModelScope.launch {
            repository.updatePlace(place)
        }
    }
    */


    suspend fun deleteUser(user: Users) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

}