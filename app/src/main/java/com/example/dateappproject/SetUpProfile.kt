package com.example.dateappproject

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dateappproject.Auth.MainActivity
import com.example.dateappproject.databinding.ActivitySetUpProfileBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

class SetUpProfile : AppCompatActivity() {

    var binding : ActivitySetUpProfileBinding? = null

    private lateinit var userViewModel: UserViewModel

    private var auth : FirebaseAuth? = null

    private var storage : FirebaseStorage? = null

    private var selectedImage : Uri? = null

    private var dialog: ProgressDialog? =null

    private var database : FirebaseDatabase?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dialog!!.setMessage("Updating Profile")
        dialog!!.setCancelable(false)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
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
            val name : String = binding!!.etUsername.text.toString()
            if(name.isEmpty()){
                binding!!.etUsername.setError("Please enter your name")

            }
            dialog!!.show()
            if(selectedImage != null){
                val reference = storage!!.reference.child("ProfileMainPicture")
                    .child(auth!!.uid!!)
                reference.putFile(selectedImage!!).addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        reference.downloadUrl.addOnCompleteListener{
                            uri ->
                            val imageUrl = uri.toString()
                            val uid = auth!!.uid
                           // val phone = auth!!.currentUser?.phoneNumber
                            val email =  auth!!.currentUser?.email.toString()
                            val name : String? = binding!!.etUsername.text.toString()
                            val user = Users(uid , name , email , "" , null, 0  , "" , "" ,"",null,imageUrl)
                            database!!.reference
                                .child("users")
                                .child(uid!!)
                                .setValue(user)
                                .addOnCompleteListener{
                                    dialog!!.dismiss()
                                    val intent =
                                        Intent(this, DateAppMainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                        }
                    }
                    else{
                        val uid = auth!!.uid
                        // val phone = auth!!.currentUser?.phoneNumber
                        val email =  auth!!.currentUser?.email.toString()
                        val name : String? = binding!!.etUsername.text.toString()
                        val user = Users(uid , name , email , "" , null, 0  , "" , "" ,"",null,"")
                        database!!.reference
                            .child("users")
                            .child(uid!!)
                            .setValue(user)
                            .addOnCompleteListener {
                                dialog!!.dismiss()
                                val intent = Intent(this@SetUpProfile
                                , DateAppMainActivity :: class.java)
                                startActivity(intent)
                                finish()
                            }
                    }
                }
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
                reference.putFile(uri!!).addOnCompleteListener {
                    task ->
                    if(task.isSuccessful){
                        reference.downloadUrl.addOnCompleteListener{
                            uri ->
                            val filePath = uri!!.toString()
                            val obj = HashMap<String , Any>()
                            obj["image"] = filePath
                            database!!.reference
                                .child("users")
                                .child(auth!!.uid!!)
                                .updateChildren(obj).addOnSuccessListener{}

                        }
                    }

                }
                binding!!.profilePicture1.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }



}