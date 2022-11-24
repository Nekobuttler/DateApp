package com.example.dateappproject

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.dateappproject.databinding.FragmentProfileShowUpBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileShowUp : Fragment() {


    private lateinit var auth : FirebaseAuth

    private var _binding: FragmentProfileShowUpBinding? = null

    private lateinit var pickSingleMediaLauncher: ActivityResultLauncher<Intent>

    private lateinit var pickMultipleMediaLauncher: ActivityResultLauncher<Intent>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                val image = binding.profilePicture
                val pictureURL = uri
                if(pictureURL.isNotEmpty()){
                    Glide.with(this)
                        .load(pictureURL)
                        .circleCrop()
                        .into(image)
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        binding.profilePicture.setOnClickListener{
            permisionPhotoPicker()
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }








        // Inflate the layout for this fragment

        pickSingleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(requireContext(), "Failed picking media.", Toast.LENGTH_SHORT).show()
                } else {
                    val uri = it.data?.data
                    Log.d("SUCCESS: ","${uri?.path}\"")
                }
            }
        pickMultipleMediaLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != Activity.RESULT_OK) {
                    Toast.makeText(requireContext(), "Failed picking media.", Toast.LENGTH_SHORT).show()
                } else {
                    val uris = it.data?.clipData ?: return@registerForActivityResult
                    var uriPaths = ""
                    for (index in 0 until uris.itemCount) {
                        uriPaths += uris.getItemAt(index).uri.path
                        uriPaths += "\n"
                    }
                    Log.d("SUCCESS: $uriPaths","")
                }
            }
        return inflater.inflate(R.layout.fragment_profile_show_up, container, false)
    }

    private fun permisionPhotoPicker() {
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            //Pedir autorizacion
            requireActivity().requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_MEDIA_LOCATION,
                    ),
                105
            )

        }else{

        }
    }

    fun pickPhoto(){
        pickMultipleMediaLauncher.launch(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .apply {
                    type = "/image/*"
                }
        )

    }


    private fun update(navView: NavigationView) {
        val view : View = navView.getHeaderView(0)
        val tvName : TextView = view.findViewById(R.id.name_user)
        val tvemail : TextView = view.findViewById(R.id.email_user)
        val image : ImageView = view.findViewById(R.id.user_profile_picture)
        val user = Firebase.auth.currentUser
        tvName.text = user?.displayName
        tvemail.text = user?.email
        val pictureURL = user?.photoUrl.toString()
        if(pictureURL.isNotEmpty()){
            Glide.with(this)
                .load(pictureURL)
                .load(tvName)
                .load(tvemail)
                .circleCrop()
                .into(image)
        }

    }


}