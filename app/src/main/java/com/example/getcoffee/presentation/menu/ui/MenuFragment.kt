package com.example.getcoffee.presentation.menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getcoffee.R
import com.example.getcoffee.databinding.FragmentMenuBinding
import com.example.getcoffee.presentation.coffeeshops.models.MenuState
import com.example.getcoffee.presentation.menu.view_model.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by viewModels()
    private lateinit var token: String
    private var placeId = -1
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var orderAdapter: OrderAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeId = it.getInt("id", -1)
            token = it.getString("token", "")
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateBack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuRecyclerView()
        setOrderRecyclerView()
        viewModel.getLocations(placeId,token)
        viewModel.state.observe(viewLifecycleOwner){state->
            manageScreenState(state)
        }

        binding.btnBack.setOnClickListener {
            navigateBack()
        }

        binding.btnToPay.setOnClickListener {
            if (viewModel.state.isInitialized && (viewModel.state.value is MenuState.OrderContent)){
                Toast.makeText(requireContext(), R.string.payment_is_ok, Toast.LENGTH_SHORT).show()
            }else{
                viewModel.stateOrder()
            }
        }
    }

    private fun manageScreenState(state: MenuState) {
        when (state) {
            is MenuState.Content -> {
                binding.tvEmpty.visibility = View.GONE
                binding.flOrder.visibility = View.GONE
                binding.tvTitle.text = getString(R.string.menu)
                binding.btnToPay.text = getString(R.string.go_to_payment)

                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                menuAdapter.submitList(state.menus)

            }

            MenuState.Error -> {
                binding.tvEmpty.visibility = View.GONE
                binding.flOrder.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            }

            MenuState.Loading -> {
                binding.tvEmpty.visibility = View.GONE
                binding.flOrder.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
            }
            is MenuState.OrderContent ->{
                binding.tvEmpty.visibility = View.GONE
                binding.flOrder.visibility = View.VISIBLE
                binding.tvTitle.text = getString(R.string.your_order)
                binding.btnToPay.text = getString(R.string.pay)

                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                orderAdapter.submitList(state.menus)
            }
            MenuState.EmptyOrder ->{
                binding.tvEmpty.visibility = View.VISIBLE
                binding.flOrder.visibility = View.GONE
                binding.tvTitle.text = getString(R.string.your_order)
                binding.btnToPay.text = getString(R.string.pay)

                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
            }
            else -> {}
        }
    }

    private fun setMenuRecyclerView() {
        menuAdapter = MenuAdapter(
            context = requireContext(),
            onAddClickListener = {menuItem, count, pos ->
            viewModel.addPosition(menuItem,count)
        },
            onRemoveClickListener = {menuItem, count, pos ->
            viewModel.removePosition(menuItem)
        })
        binding.recyclerView.adapter = menuAdapter
        binding.recyclerView.setHasFixedSize(true)
        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }
    }

    private fun setOrderRecyclerView() {
        orderAdapter = OrderAdapter(
            context = requireContext(),
            onAddClickListener = {menuItem, count, pos ->
                viewModel.addPosition(menuItem,count)
            },
            onRemoveClickListener = {menuItem, count, pos ->
                viewModel.removePosition(menuItem)
            })
        binding.recyclerViewOrder.adapter = orderAdapter
        binding.recyclerViewOrder.layoutManager = LinearLayoutManager(requireContext())
        //binding.recyclerViewOrder.setHasFixedSize(false)
        val itemAnimator = binding.recyclerViewOrder.itemAnimator
        if (itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }
    }

    private fun navigateBack(){
        if (viewModel.state.isInitialized && (viewModel.state.value is MenuState.OrderContent) || viewModel.state.isInitialized && (viewModel.state.value is MenuState.EmptyOrder) ){
            viewModel.stateContent()
        }
        else{
            findNavController().navigateUp()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}