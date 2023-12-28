package com.example.getcoffee.presentation.authorization.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.getcoffee.databinding.FragmentAuthorizationBinding
import com.example.getcoffee.presentation.authorization.models.AuthState
import com.example.getcoffee.presentation.authorization.view_model.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.state.observe(viewLifecycleOwner) { state ->
            manageState(state)
        }

        binding.btnSighIn.setOnClickListener {
            val login = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.signIn(login, password)
        }

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun manageState(state: AuthState){
        when(state){
            is AuthState.Success -> {
                val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToCoffeeShopsFragment(state.token)
                findNavController().navigate(action)
            }
            AuthState.NotSuccess -> {
                Toast.makeText(requireContext(), "Не удалось войти", Toast.LENGTH_SHORT).show()
            }
            AuthState.Default -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}