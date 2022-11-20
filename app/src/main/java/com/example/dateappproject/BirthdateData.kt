package com.example.dateappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.dateappproject.databinding.FragmentBirthdateDataBinding
import com.example.dateappproject.databinding.NameBiographyDataBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel


class BirthdateData : Fragment() {



    private var _binding: FragmentBirthdateDataBinding? = null

    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel = ViewModelProvider(this).get(UserViewModel :: class.java)
        _binding = FragmentBirthdateDataBinding.inflate(inflater , container , false)




        return binding.root
    }

    private fun getDataCalendar(){

    }


}