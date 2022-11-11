package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity


import com.example.dateappproject.databinding.LoginFragmentBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal

class LoginMainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    //Definimos un objeto para acceder a los elementos

    private lateinit var binding: LoginFragmentBinding

    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding=LoginFragmentBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Inicializar la autenticacion

        FirebaseApp.initializeApp(this)

        auth = Firebase.auth

        //Definimos los eventos Onclick

        binding.btSignin.setOnClickListener{
            SignIn()
        }

        binding.btLogin.setOnClickListener{
            LogIn()
        }



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
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)

        }else{

        }
    }

    private fun LogIn() {

        val email= binding.etEmail.text.toString()
        val password= binding.edPassword.text.toString()


        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                    task -> if(task.isSuccessful){
                Log.d("AUTENTICANDO ", "se autentico")
                val user = auth.currentUser
                refresh(user)
            } else{

                Toast.makeText(baseContext, "Fallo", Toast.LENGTH_LONG).show()
                refresh(null)
            }

            }
    }


    public override fun onStart(){

        super.onStart()
        val user = auth.currentUser
        refresh(user)
    }
}