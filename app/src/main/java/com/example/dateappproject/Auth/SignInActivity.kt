package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.SetUpProfile
import com.example.dateappproject.SetUpProfile2
import com.example.dateappproject.databinding.ActivitySignInBinding
import com.example.dateappproject.model.Users
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth : FirebaseAuth

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)


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
                    val document : DocumentReference =  firestore.collection("Users").document()
                    // if the creation of the account is succesful create the account with the data
                    var User:Users = Users(auth.currentUser!!.uid , "" , auth.currentUser!!.email , "" , null, 0  , "" , "" ,"",null,"")
                    SaveUser(User)
                    val user= auth.currentUser
                    val intent = Intent(this, SetUpProfile::class.java)
                    startActivity(intent)
                }

                else{
                    Toast.makeText(baseContext,"Fallo", Toast.LENGTH_LONG).show()
                    refresh(null)
                }
            }
    }

    private fun refresh(user: FirebaseUser?)
    {
        if(user != null ){
        firestore.collection("Users")
            .document(auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                    documentSnapshot ->
                val User = documentSnapshot.toObject<Users>()  // if the data in the snapshot like name and others is empty then
                if(User?.name!!.isEmpty() ){                   // go to setup the profil){
                    val intent = Intent(this, SetUpProfile::class.java)
                    startActivity(intent)

                }else{
                    val intent = Intent(this, DateAppMainActivity::class.java)
                    startActivity(intent)
                }

            }

        //Tratar de comprobar si los datos de img ruta y nombre estan llenos
        //Hacer snapshot guardar datos en variable temporal y luego comparar dentro de esta?


    }else{


    }
}
    fun SaveUser(User: Users) { //Primero objeto luego clase que pertenece

        val document: DocumentReference

        if (User.id!!.isEmpty()) { //Nuevo regitro

            document = firestore
                .collection("Users")
                .document()
            document.set(User)
            //For Updates
        } else {
            document = firestore
                .collection("Users")
                .document(auth.currentUser!!.uid)
            document.set(User)
        }

        document.set(User) // Update or Save the user in the document find o created
            .addOnSuccessListener {
            Log.d(
                "Add User",
                "User Added"
            )
        }
            .addOnCanceledListener {
                Log.e(
                    "Add User",
                    "User Not Added"
                )
            }

    }
    


    public override fun onStart(){
        super.onStart()
        val user = auth.currentUser
        refresh(user)
    }




}


