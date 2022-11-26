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
import com.google.firebase.ktx.Firebase

class UsersSelection : AppCompatActivity() {

    private var binding: ActivityUsersSelectionBinding? = null

    private lateinit var userViewModel: UserViewModel

    var database : FirebaseDatabase? =null

    var users : ArrayList<Users>? = null

    var usersAdapter : UserSAdapter? = null

    var diaglog: ProgressDialog? = null

    var user : Users? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityUsersSelectionBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        diaglog = ProgressDialog(this@UsersSelection)
        diaglog!!.setMessage("Uploading Image...")
        diaglog!!.setCancelable(false)
        database = FirebaseDatabase.getInstance()
        users = ArrayList<Users>()
        usersAdapter = UserSAdapter(this@UsersSelection , users!!)
        val layoutManager  = GridLayoutManager(this@UsersSelection , 2)
        binding!!.usersList.layoutManager = layoutManager
        database!!.reference.child("users")
            .child(FirebaseAuth.getInstance().uid!!)
            .addValueEventListener(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(Users :: class.java)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        binding!!.usersList.adapter = usersAdapter
        database!!.reference.child("users").addValueEventListener(
            object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    users!!.clear()
                    for(snapshot1 in snapshot.children){
                        val user : Users? = snapshot1.getValue(Users :: class.java)
                        if(!user!!.id.equals(FirebaseAuth.getInstance().uid)) {
                            users!!.add(user)
                        }
                        usersAdapter!!.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference
            .child("presence")
            .child(currentId!!).setValue("Online")
    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference
            .child("presence")
            .child(currentId!!).setValue("Offline")
    }
}







