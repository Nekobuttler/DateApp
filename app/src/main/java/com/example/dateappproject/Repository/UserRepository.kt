package com.example.dateappproject.Repository

import androidx.lifecycle.MutableLiveData
import com.example.dateappproject.data.UsersDao
import com.example.dateappproject.model.Users

class UserRepository (private val usersDao : UsersDao ) {

    val getAllData : MutableLiveData<List<Users>> = usersDao.getUsers()


    fun addUser (user: Users ){
        usersDao.SaveUser(user)
    }

    fun deleteUser(user : Users){
        usersDao.DeleteUser(user)
    }


}