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

class UserSAdapter(var context: Context , var userList :ArrayList<Users>)
    : RecyclerView.Adapter<UserSAdapter.UserViewHolder>() {




    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        var Items = LayoutInflater.from(context).inflate(R.layout.profile_card
        ,parent
            ,false)

        return UserViewHolder(Items)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val actualUser = userList[position]
        holder.binding.username.text = actualUser.name
        holder.binding.Biography.text = actualUser.biography
        Glide.with(context).load(actualUser.profileMainPicture)
            .placeholder(R.drawable.music_video_asmr_man)
            .into(holder.binding.profileUser)
        holder.GetData(actualUser)
    }

    override fun getItemCount(): Int = userList.size


    fun setUsers( users : ArrayList<Users>){
        userList = users

        notifyDataSetChanged() // se notifica que el conjunto de datos cambio y se redibuja toda la lista
    }

}