package com.example.dateappproject.ui.Users

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dateappproject.R
import com.example.dateappproject.databinding.FragmentDataUserBinding
import com.example.dateappproject.databinding.FragmentDataUserUpdateBinding
import com.example.dateappproject.model.Users
import com.example.dateappproject.viewmodel.UserViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.ktx.Firebase


class DataUserFragment : Fragment() {private var _binding: FragmentDataUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var placeViewModel: UserViewModel


    private lateinit var tomarFotoActivity : ActivityResultLauncher<Intent>




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        placeViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        _binding = FragmentDataUserBinding.inflate(inflater, container, false)
        //val root: View = binding.root

        binding.btCreateUser.setOnClickListener{
            //createUser()
        }



/*
        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }
        */




        return binding.root
    }

    /*
    private fun GPSTurnOn() {
        if(requireActivity().
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
            && requireActivity().
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ){
            //Pedir autorizacion
            requireActivity().requestPermissions(arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION ,
                Manifest.permission.ACCESS_FINE_LOCATION),
                105)

        }else{
            val fusedLocalClient : FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocalClient.lastLocation.addOnSuccessListener {
                    location : Location? ->
                if(location != null){
                    binding.tvLatitud.text = "${location.latitude}"
                    binding.tvLongitud.text = "${location.longitude}"
                    binding.tvAltura.text = "${location.altitude}"

                }else{
                    binding.tvLatitud.text = "0.0"
                    binding.tvLongitud.text = "0.0"
                    binding.tvAltura.text = "0.0"
                }
            }
        }

    }

    */

    /*
    private fun subirAudio(){
        val audioFile = audioUtiles.audioFile
        if(audioFile.exists() && audioFile.isFile && audioFile.canRead()){
            val rutaLocal = Uri.fromFile(audioFile) //ruta del archivo local....
            val rutaNube = "placesApp/${Firebase.auth.currentUser?.email}/audios/${audioFile.name}"

            val reference : StorageReference = Firebase.storage.reference.child(rutaNube) // referencia de firebase

            reference.putFile(rutaLocal)
                .addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        val rutaAudio = it.toString()
                        subeImagen(rutaAudio)
                    }
                }.addOnFailureListener(
                    subeImagen("")
                )
        }else{
            subeImagen("")
        }
    }

     */
/*
    private fun subeImagen(): OnFailureListener {
        //binding.msgMensaje.text = "Subiendo Imagen" // -
        val imagenFile = imagenUtiles.imagenFile
        if(imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val rutaLocal = Uri.fromFile(imagenFile) //ruta del archivo local....
            val rutaNube = "placesApp/${Firebase.auth.currentUser?.email}/imagenes/${imagenFile.name}"

            val reference : StorageReference = Firebase.storage.reference.child(rutaNube) // referencia de firebase

            reference.putFile(rutaLocal)
                .addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        val rutaPublicaImagen = it.toString()
                        addPlace(rutaPublicaAudio , rutaPublicaImagen)
                    }
                }.addOnFailureListener(
                    addPlace(rutaPublicaAudio,"")
                )
        }else{
            addPlace(rutaPublicaAudio,"")
        }
    }
*/
    //Registro de los datos en la base de datos
    /*
    private fun createUser(){
        val name = binding.etName.text.toString()
        val number = binding.edNumber.text.toString()
        val biography = binding.edBiography.text.toString()
        val birht = binding.edBirthdate.date.toInt()
        val auth = Firebase.auth.currentUser
        val email = auth?.email.toString()

        if(name.isNotEmpty() && number.isNotEmpty()){//Al menos se tiene un nombre
            val user = Users("" , name , email , number , null , birht , biography,"","")
            UserViewModel.saveUser(user)

            Toast.makeText(requireContext(),getString(R.string.msg_user_added, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_dataUserFragment_to_nav_User)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_data), Toast.LENGTH_LONG).show()
        }
    }
    */


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}