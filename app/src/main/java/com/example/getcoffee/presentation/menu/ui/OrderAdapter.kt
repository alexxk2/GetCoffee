package com.example.getcoffee.presentation.menu.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getcoffee.R
import com.example.getcoffee.databinding.OrderItemBinding
import com.example.getcoffee.domain.models.MenuItem

class OrderAdapter(
    private val context: Context,
    private val onAddClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
    private val onRemoveClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
) : ListAdapter<MenuItem, OrderAdapter.OrderViewHolder>(DiffCallBack) {

   inner class OrderViewHolder(val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: MenuItem,
            onAddClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
            onRemoveClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
            position: Int
        ) {
            binding.tvProductName.text = item.name
            binding.tvPrice.text = context.getString(R.string.price,item.price)
            binding.tvProductCount.text = item.count.toString()

            binding.btnAddProduct.setOnClickListener {
                var count = binding.tvProductCount.text.toString().toInt()
                count++
                binding.tvProductCount.text = count.toString()

                onAddClickListener(item,count,position)
            }
            binding.btnRemoveProduct.setOnClickListener {
                var count = binding.tvProductCount.text.toString().toInt()
                if (count>0) count--
                binding.tvProductCount.text = count.toString()

                onRemoveClickListener(item,count,position)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =OrderItemBinding.inflate(inflater,parent,false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onAddClickListener, onRemoveClickListener,position)
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<MenuItem>() {

            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return false
            }
        }
    }
}