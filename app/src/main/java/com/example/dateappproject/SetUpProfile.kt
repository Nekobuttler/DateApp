package com.example.dateappproject

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.dateappproject.databinding.ActivitySetUpAgeGenderBinding
import com.example.dateappproject.databinding.ActivitySetUpProfileBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.collections.HashMap

class SetUpProfile : AppCompatActivity() {

    var binding : ActivitySetUpProfileBinding? = null

    private lateinit var userViewModel: UserViewModel

    private var auth : FirebaseAuth? = null

    private var firestore : FirebaseFirestore? = FirebaseFirestore.getInstance()



    private var selectedImage : Uri? = null

    //private var dialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        userViewModel = ViewModelProvider(this).get((UserViewModel :: class.java))
      //  dialog!!.setMessage("Updating Profile")
       // dialog!!.setCancelable(false)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()



        //Obtener la imagen del image view apartir de un intent
        binding!!.profilePicture1.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent , 45)
        }
        //Verificar antes de continuar si el nombre y la imagen se han posteado
        binding!!.btContinueProfile.setOnClickListener{

            saveUser()}


    }
    private fun saveUser(){

        val name : String = binding!!.etUsername.text.toString()
        if(name.isEmpty()){
            binding!!.etUsername.setError("Please enter your name")

        }
        //dialog!!.show()
        if(selectedImage != null){
            val  document : DocumentReference
            document = firestore!!.collection("Users").document(auth!!.currentUser!!.uid)
            val rutaNube = "dateApp/${Firebase.auth.currentUser!!.uid}/images/${selectedImage}"
            val reference: StorageReference = Firebase.storage.reference.child(rutaNube)
            reference.putFile(selectedImage!!).addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener{
                            uri ->
                        val imageUrl = uri.toString()
                        val uid = auth!!.currentUser?.uid
                        // val phone = auth!!.currentUser?.phoneNumber
                        val email =  auth!!.currentUser?.email.toString()
                        val name : String? = binding!!.etUsername.text.toString()
                        val user = Users(uid , name , email , "" , null, 0  , "" , "" ,"","",imageUrl)
                        val doc : DocumentReference = firestore!!.collection("Users").document(uid.toString())
                        if(doc != null){
                            doc.set(user)
                        }else {

                            userViewModel.saveUser(user)
                        }

                        //     dialog!!.dismiss()
                        val intent = Intent(this, ActivitySetUpAgeGenderBinding::class.java)
                        startActivity(intent)

                       // val intent =
                         //   Intent(this, DateAppMainActivity::class.java)
                       // startActivity(intent)
                        finish()


                    }
                }
                .addOnFailureListener{
                    val uid = auth!!.uid
                    // val phone = auth!!.currentUser?.phoneNumber
                    val email =  auth!!.currentUser?.email.toString()
                    val name : String? = binding!!.etUsername.text.toString()
                    val user = Users(uid , name , email , "" , null, 0  , "" , "" ,"",null,"")
                    userViewModel.saveUser(user)
                    //   dialog!!.dismiss()
                    val intent = Intent(this@SetUpProfile
                        , ActivitySetUpAgeGenderBinding :: class.java)
                    startActivity(intent)
                    finish()

                }
            }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        auth = FirebaseAuth.getInstance()

        if(data!=null){
            if(data.data != null){
                val uri = data.data  //filePath
                val storage = FirebaseStorage.getInstance()
                val time = Date().time
                val reference = storage.reference
                    .child("Profile")
                    .child(time.toString()+"")

                binding!!.profilePicture1.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }



}