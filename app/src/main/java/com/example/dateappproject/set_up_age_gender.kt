package com.example.dateappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dateappproject.databinding.ActivitySetUpAgeGenderBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*

private lateinit var binding : ActivitySetUpAgeGenderBinding

private lateinit var auth : FirebaseAuth

private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

private lateinit var userViewModel: UserViewModel

class set_up_age_gender : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetUpAgeGenderBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userViewModel = ViewModelProvider(this).get((UserViewModel :: class.java))
        val checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId


        binding.pickDateED.setOnClickListener{
            showDatePickerDialog()
        }




        binding.continueButton.setOnClickListener{
            saveUser()
        }

        setContentView(binding.root)




    }

    private fun saveUser() {
        firestore.collection("Users")
            .document(auth.currentUser!!.uid)
            .get().addOnSuccessListener { documentSnapshot ->
                var User = documentSnapshot.toObject<Users>()
                User!!.birthdate = binding.pickDateED.toString()

                if(binding.radioMen.isChecked){
                    User.gender = binding.radioMen.text.toString()
                }
                if(binding.radioNonBi.isChecked){
                    User.gender = binding.radioMen.text.toString()
                }
                if(binding.radioWomen.isChecked){
                    User.gender = binding.radioMen.text.toString()
                }
                userViewModel.saveUser(User)
                var doc : DocumentReference = firestore.collection("Users")
                    .document(auth.currentUser!!.uid)
                doc.set(User).addOnSuccessListener {
                    val intent = Intent(this , DateAppMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }


            }

    }


    private fun showDatePickerDialog(){

        val datePicker =
            DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }

        datePicker.show(supportFragmentManager , "datePicker")
    }

    fun onDateSelected(day:Int , month : Int , year:Int){

        binding.pickDateED.setText( "$day/$month/$year")
    }

}