package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.SetUpProfile
import com.example.dateappproject.databinding.LoginActivityBinding
import com.example.dateappproject.model.Users

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.security.Principal

class LoginMainActivity : AppCompatActivity() {



    //Definimos un objeto para acceder a los elementos

    private lateinit var binding: LoginActivityBinding


    private lateinit var auth : FirebaseAuth

    private var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()


    }

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
            firestore.collection("Users")
                .document(auth.currentUser!!.uid)
                .get().addOnSuccessListener {
                        documentSnapshot ->
                    val User = documentSnapshot.toObject<Users>()
                    if(User?.name!!.isEmpty() ||
                        User?.name!!.isEmpty()){
                        val intent = Intent(this, SetUpProfile::class.java)
                        startActivity(intent)

                }

                    }

                //Tratar de comprobar si los datos de img ruta y nombre estan llenos
                //Hacer snapshot guardar datos en variable temporal y luego comparar dentro de esta? 
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