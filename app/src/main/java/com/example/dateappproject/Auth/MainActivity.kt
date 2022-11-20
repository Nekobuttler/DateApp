package com.example.dateappproject.Auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.R
import com.example.dateappproject.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.Principal


class MainActivity :AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)

        auth = Firebase.auth

        setContentView(binding.root)

        //setContentView(binding.root)

        binding.btLogin.setOnClickListener{

            goLogin()
        }
        binding.btSignIn.setOnClickListener{

            goSignin()
        }

        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this , gso)

        binding.btgoogle.setOnClickListener{
            googleSignIn()
        }

    }

    private fun googleSignIn() {
        val signInIntent =googleSignInClient.signInIntent
        startActivityForResult(signInIntent , 5000) // request code
    }


    private fun firebaseAuthWithGoogle(idToken : String){
        val credential =GoogleAuthProvider.getCredential(idToken , null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
            task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    refresh(user)
                }else{
                    refresh(null)
                }
            }
    }

    private fun OnActivityResult(requestCode: Int,resultCode:Int , data: Intent){
        super.onActivityResult(requestCode,resultCode,data)
        if(requestCode == 5000){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){

            }

        }
    }


    private fun goLogin(){
        val intent = Intent(this, LoginMainActivity::class.java)
        startActivity(intent)
    }

    private fun goSignin(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }


    private fun refresh(user: FirebaseUser?) {
        if(user != null){
            val intent = Intent(this, DateAppMainActivity::class.java)
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