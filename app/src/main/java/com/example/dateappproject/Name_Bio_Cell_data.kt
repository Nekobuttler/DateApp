package com.example.dateappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dateappproject.databinding.NameBiographyDataBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NameBiographyDataBinding : Fragment() {


    private var _binding: NameBiographyDataBinding? = null

    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    //private lateinit var tomarFotoActivity : ActivityResultLauncher<Intent>

//    private lateinit var imagenUtiles: ImagenUtiles


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel = ViewModelProvider(this).get(UserViewModel :: class.java)
        _binding = NameBiographyDataBinding.inflate(inflater , container , false)


        binding.btContinue.setOnClickListener {

            uptUser()
        }


        return binding.root
    }

    private fun uptUser(){
        val name = binding.edNameUser.text.toString()
        val biography = binding.edBiographyFr.text.toString()
        val phone = binding.edPhone.text.toString()


        if(name.isNotEmpty()){//Al menos se tiene un nombre
            val users = Users("" , name , Firebase.auth.currentUser?.email.toString() , phone , null, 0  , biography , "" ,"","")
            userViewModel.saveUser(users)

            Toast.makeText(requireContext(),getString(R.string.msg_data_user_act), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragment_name_date_to_birthdateData)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_user_added), Toast.LENGTH_LONG).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}