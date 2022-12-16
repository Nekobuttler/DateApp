package com.example.dateappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dateappproject.databinding.ActivityUpdateProfileBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject



class UpdateProfile : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateProfileBinding

    private lateinit var auth : FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()

        userViewModel =  ViewModelProvider(this).get(UserViewModel ::class.java)

        firestore.collection("Users")
            .document(auth.currentUser!!.uid.toString())
            .get().addOnSuccessListener {
                    documentSnapshot ->
                val obtainUser = documentSnapshot.toObject<Users>()
                binding.NameHolder.setText(obtainUser!!.name.toString())
                binding.BiographyHolder.setText(obtainUser.biography.toString())
                binding.holderBirthday.setText( obtainUser.birthdate.toString())
                binding.generoHolder.setText(obtainUser.gender.toString())
                Glide.with(this)
                    .load(obtainUser.profileMainPicture)
                    .circleCrop()
                    .into(binding.imageHolder)


            }.addOnFailureListener{
                Toast.makeText(this@UpdateProfile , "No se pudo recuperar a su usuario intentelo de nuevo mas tarde" , Toast.LENGTH_LONG).show()
                val intent  = Intent(this , DateAppMainActivity::class.java)
                startActivity(intent)
            }

        binding.imageHolder.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent , 45)
        }

        binding.deleteProfile.setOnClickListener{
            delete()

        }




        setContentView(binding.root)
    }

        fun delete(){
            firestore.collection("Users")
                .document(auth.currentUser!!.uid.toString())
                .get().addOnSuccessListener {
                        documentSnapshot ->
                    val obtainUser = documentSnapshot.toObject<Users>()
                    userViewModel.deleteUser(obtainUser!!)
                    val doc : DocumentReference =   firestore.collection("Users")
                        .document(auth.currentUser!!.uid.toString())
                    doc.delete()
                    FirebaseAuth.getInstance().currentUser!!.delete()
                    FirebaseAuth.getInstance().signOut();

        }
        }
}