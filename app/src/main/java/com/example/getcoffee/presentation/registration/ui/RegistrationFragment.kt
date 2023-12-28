package com.example.getcoffee.presentation.registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.getcoffee.R
import com.example.getcoffee.databinding.FragmentRegistrationBinding
import com.example.getcoffee.presentation.authorization.models.AuthState
import com.example.getcoffee.presentation.registration.view_model.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner){state->
           manageState(state)
        }

        binding.btnSighUp.setOnClickListener {
            val login = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatedPassword = binding.etRepeatPassword.text.toString()

            if ( repeatedPassword != password){
                Toast.makeText(requireContext(),R.string.passwords_are_not_equal, Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.signUp(login, password)
            }
        }

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }


        binding.etRepeatPassword.addTextChangedListener {
             if (it.toString() != binding.etPassword.text.toString()){
                      binding.tilRepeatPassword.error = getString(R.string.passwords_are_not_equal)
                  }
                  else{
                      binding.tilRepeatPassword.error = null
                  }
        }
    }

    private fun manageState(state: AuthState){
        when(state){
            is AuthState.Success -> {
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToCoffeeShopsFragment(state.token)
                findNavController().navigate(action)
            }
            AuthState.NotSuccess -> {
                Toast.makeText(requireContext(), "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show()
            }
            AuthState.Default -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}