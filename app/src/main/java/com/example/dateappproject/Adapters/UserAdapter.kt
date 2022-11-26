package com.example.dateappproject.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dateappproject.R
import com.example.dateappproject.databinding.ProfileCardBinding
import com.example.dateappproject.model.Users

class UserAdapter
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var userList = emptyList<Users>()

    inner class UserViewHolder(private val itemBinding: ProfileCardBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        val binding : ProfileCardBinding = ProfileCardBinding.bind(itemView)

        fun GetData(user : Users){
            binding.username.text  = user.name
            binding.Biography.text = user.biography


            Glide.with(binding.root.context)
                .load(user.profileMainPicture)
                .circleCrop()
                .into(binding.profileUser)
            binding.cardViewUsers.setOnClickListener {

               /* val accion = PlaceFragmentDirections.
                actionNavPlaceToUpdatePlaceFragment(place)

                itemView.findNavController().navigate(accion)*/
                Log.d("Information Message","Se ha trasladado a otra pesta;a ")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var Items =
            ProfileCardBinding.inflate(LayoutInflater.from(parent.context),
             parent ,
            false)

        return UserViewHolder(Items)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val ActualUser = userList[position]
        holder.GetData(ActualUser)
    }

    override fun getItemCount(): Int = userList.size


    fun setUsers( users : List<Users>){
        userList = users

        notifyDataSetChanged() // se notifica que el conjunto de datos cambio y se redibuja toda la lista
    }

}