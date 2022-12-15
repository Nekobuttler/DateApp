package com.example.dateappproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.dateappproject.databinding.FragmentBirthdateDataBinding
import com.example.dateappproject.databinding.NameBiographyDataBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel
import java.util.Calendar;
import java.util.Date;



class BirthdateData : Fragment() {



    private var currentTime : Date = Calendar.getInstance().time

    private var _binding: FragmentBirthdateDataBinding? = null

    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private val agelimit = 16

    private var datePicker = binding.datePicker



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       userViewModel = ViewModelProvider(this).get(UserViewModel :: class.java)
        _binding = FragmentBirthdateDataBinding.inflate(inflater , container , false)


        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH)) {
            view,year,month,day ->
            val month = month+1
            binding.birhtdateShw.setText("$year/$month/$day")
        }



        return binding.root
    }




        }


/*
    private fun getAge(year : Int, month  : Int, day : Int): Int {
        var dateOfBirth : Calendar  = Calendar.getInstance()
        var today : Calendar  = Calendar.getInstance()

        dateOfBirth.set(year, month, day);


        var age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        //if (today.get(Calendar.DAY_OF_YEAR) < dateOfBirth.get(Calendar.DAY_OF_YEAR)) {
        //    age--;
       // }

        return age;
    }*/

