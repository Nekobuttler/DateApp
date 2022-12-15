package com.example.dateappproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dateappproject.Adapters.UserAdapter
import com.example.dateappproject.databinding.FragmentUsersViewBinding
import com.example.dateappproject.ui.viewmodel.UserViewModel


class UsersViewFragment : Fragment() {

    private var _binding: FragmentUsersViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var usersViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        usersViewModel = ViewModelProvider(this).get(UserViewModel ::class.java)
        _binding = FragmentUsersViewBinding.inflate(inflater, container, false)


        val userAdapter = UserAdapter()

        val recycler = binding.usersList
        recycler.adapter = userAdapter

        recycler.layoutManager = LinearLayoutManager(requireContext())

        usersViewModel.getUsers.observe(this.viewLifecycleOwner) {
                users -> userAdapter.setUsers(users)
        }

        return binding.root
    }


    }
