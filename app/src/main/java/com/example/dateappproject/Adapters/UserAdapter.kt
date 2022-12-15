package com.example.dateappproject.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dateappproject.*
import com.example.dateappproject.databinding.ActivityChatBinding
import com.example.dateappproject.databinding.ProfileCardBinding
import com.example.dateappproject.model.Users

class UserAdapter
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var userList = emptyList<Users>()

        inner class UserViewHolder(private val itemBinding: ProfileCardBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {



        fun GetData(user : Users){
            itemBinding.username.text  = user.name
            itemBinding.Biography.text = user.biography

            if(user.profileMainPicture.isNullOrEmpty()) {
                Glide.with(itemBinding.root.context)
                    .load(user.profileMainPicture)
                    .load("https://cdn.pixabay.com/photo/2017/11/10/05/48/user-2935527_960_720.png")
                    .circleCrop()
                    .into(itemBinding.profileUser)
            }else{
                Glide.with(itemBinding.root.context)
                    .load(user.profileMainPicture)
                    .load(user.profileMainPicture)
                    .circleCrop()
                    .into(itemBinding.profileUser)
            }
            itemBinding.cardViewUsers.setOnClickListener {

                val action =
                UsersViewFragmentDirections.actionUsersViewFragmentToChatActivity(user)


                itemView.findNavController().navigate(action)

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
        //holder.itemView.setOnClickListener {


        //}
    }

    override fun getItemCount(): Int = userList.size


    fun setUsers( users : List<Users>){
        userList = users

        notifyDataSetChanged() // se notifica que el conjunto de datos cambio y se redibuja toda la lista
    }

}