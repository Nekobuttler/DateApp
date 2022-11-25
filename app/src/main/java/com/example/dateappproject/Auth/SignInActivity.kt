package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.R
import com.example.dateappproject.SetUpProfile
import com.example.dateappproject.databinding.ActivitySignInBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)


        FirebaseApp.initializeApp(this)

        auth = Firebase.auth


        binding.btLoginSign.setOnClickListener{
            goLogin()
        }

        binding.btSignin.setOnClickListener{
            SignIn()
        }

        }

    private fun goLogin(){
        val intent = Intent(this , LoginMainActivity::class.java)
        startActivity(intent)


    }

    private fun SignIn() {

        //Recupero la informacion que le usuario escribio
        val email= binding.etEmail.text.toString()
        val password= binding.edPassword.text.toString()

        //Crear el usuario
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val user= auth.currentUser
                    refresh(user)
                }else{
                    Toast.makeText(baseContext,"Fallo", Toast.LENGTH_LONG).show()
                    refresh(null)
                }
            }
    }

    private fun refresh(user: FirebaseUser?) {
        if(user != null){

            val intent = Intent(this, SetUpProfile::class.java)
            startActivity(intent)

        }else{

        }
    }
    


    public override fun onStart(){
        super.onStart()
        val user = auth.currentUser
        refresh(user)
    }




}


