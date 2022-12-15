package com.example.dateappproject.ui.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.dateappproject.DateAppMainActivity
import com.example.dateappproject.R
import com.example.dateappproject.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

private var _binding: FragmentGalleryBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)



    _binding = FragmentGalleryBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textGallery
    galleryViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }

      _binding!!.btUserlist.setOnClickListener {
          findNavController().navigate(R.id.action_nav_gallery_to_usersViewFragment)

      }
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}