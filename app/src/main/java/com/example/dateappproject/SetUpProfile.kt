package com.example.dateappproject

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dateappproject.databinding.ActivitySetUpProfileBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class SetUpProfile : AppCompatActivity() {

    var binding : ActivitySetUpProfileBinding? = null



    private lateinit var userViewModel: UserViewModel

    private var storage : FirebaseStorage? = null

    private var selectedImage : Uri? = null

    private var dialog: ProgressDialog? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up_profile)
    }



}