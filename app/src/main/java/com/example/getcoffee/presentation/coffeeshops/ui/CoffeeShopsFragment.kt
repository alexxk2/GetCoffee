package com.example.getcoffee.presentation.coffeeshops.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getcoffee.databinding.FragmentCoffeeshopsBinding
import com.example.getcoffee.presentation.coffeeshops.models.CoffeeShopsState
import com.example.getcoffee.presentation.coffeeshops.view_model.CoffeeShopsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeShopsFragment : Fragment() {
    private var _binding: FragmentCoffeeshopsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoffeeShopsViewModel by viewModels()
    private lateinit var adapter: CoffeeShopsAdapter
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = "Bearer ${it.getString("token","")}"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoffeeshopsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        viewModel.state.observe(viewLifecycleOwner){state->
            manageScreenState(state)
        }
        viewModel.getLocations(token)

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.btnOnMap.setOnClickListener {
            val action = CoffeeShopsFragmentDirections.actionCoffeeShopsFragmentToCoffeeShopsMapFragment(token)
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView(){
        adapter = CoffeeShopsAdapter {location ->
            val action = CoffeeShopsFragmentDirections.actionCoffeeShopsFragmentToMenuFragment(location.id,token)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun manageScreenState(state: CoffeeShopsState){
        when(state){
            is CoffeeShopsState.Content -> {
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                adapter.submitList(state.locations)
            }
            CoffeeShopsState.Error -> {
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            }
            CoffeeShopsState.Loading -> {
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}