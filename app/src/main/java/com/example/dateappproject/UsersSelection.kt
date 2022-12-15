package com.example.dateappproject

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dateappproject.Adapters.UserAdapter
import com.example.dateappproject.Adapters.UserSAdapter
import com.example.dateappproject.databinding.ActivityDateAppMainBinding
import com.example.dateappproject.databinding.ActivityUsersSelectionBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UsersSelection : AppCompatActivity() {

    private var binding: ActivityUsersSelectionBinding? = null

    private lateinit var userViewModel: UserViewModel


    var firestore : FirebaseFirestore? =null


    var users : ArrayList<Users>? = null


    var diaglog: ProgressDialog? = null

    var user : Users? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityUsersSelectionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        firestore = FirebaseFirestore.getInstance()
        diaglog = ProgressDialog(this@UsersSelection)
        diaglog!!.setMessage("Uploading Image...")
        diaglog!!.setCancelable(false)

        //userViewModel = ViewModelProvider(this).get(userViewModel :: class.java)

        userViewModel = userViewModel
        val usersAdapter = UserAdapter()

        users = ArrayList<Users>()

        val layoutManager  = GridLayoutManager(this@UsersSelection , 2)
        binding!!.usersList.layoutManager = layoutManager

        val recycler = binding!!.usersList
        recycler.adapter = usersAdapter



        userViewModel.getUsers.observe(this){
            users -> usersAdapter.setUsers(users)
}



            }

    /*
    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        firestore?.collection("Users").document(FirebaseAuth.getInstance().uid.toString()).set()
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference
            .child("presence")
            .child(currentId!!).setValue("Offline")
    }

*/

    }















