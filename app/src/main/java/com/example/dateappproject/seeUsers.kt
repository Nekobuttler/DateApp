package com.example.dateappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dateappproject.Adapters.UserAdapter
import com.example.dateappproject.databinding.FragmentSeeUsersBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel

class seeUsers : Fragment() {
    private var _binding: FragmentSeeUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        _binding = FragmentSeeUsersBinding.inflate(inflater, container, false)
        //val root: View = binding.root




        //ACTIVAR EL RECYCLER VIEW usando el adapter

        val userAdapter = UserAdapter()

        val recycler = binding.viewUsers
        recycler.adapter = userAdapter

        recycler.layoutManager = LinearLayoutManager(requireContext())

        userViewModel.getUsers.observe(viewLifecycleOwner) {
                users -> userAdapter.setUsers(users)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}