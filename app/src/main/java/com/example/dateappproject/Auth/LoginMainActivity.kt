package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.databinding.LoginActivityBinding

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal

class LoginMainActivity : AppCompatActivity() {



    //Definimos un objeto para acceder a los elementos

    private lateinit var binding: LoginActivityBinding


    private lateinit var auth : FirebaseAuth

    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding=LoginActivityBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        //Inicializar la autenticacion

        FirebaseApp.initializeApp(this)

        auth = Firebase.auth

        //Definimos los eventos Onclick



        binding.btLogin.setOnClickListener{
            LogIn()
        }

        binding.btSignInLO.setOnClickListener{
            goSignIn()
        }

    }




    private fun refresh(user: FirebaseUser?) {
        if(user != null ){
            val intent = Intent(this, DateAppMainActivity::class.java)
            startActivity(intent)

        }else{

        }
    }

    private fun goSignIn(){
        val intent = Intent(this , SignInActivity::class.java)
        startActivity(intent)


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