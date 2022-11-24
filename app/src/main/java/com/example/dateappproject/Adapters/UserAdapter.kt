package com.example.dateappproject.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dateappproject.R
import com.example.dateappproject.databinding.ProfileCardBinding
import com.example.dateappproject.model.Users

class UserAdapter(var context : Context , var userList: MutableLiveData<List<Users>>)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var UsersList = emptyList<Users>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding : ProfileCardBinding = ProfileCardBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var Items = LayoutInflater.from(context).inflate(R.layout.profile_card,
        parent , false)

        return UserViewHolder(Items)


    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return UsersList.size
    }
}