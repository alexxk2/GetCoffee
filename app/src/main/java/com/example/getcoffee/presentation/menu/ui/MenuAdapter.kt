package com.example.getcoffee.presentation.menu.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.getcoffee.R
import com.example.getcoffee.databinding.MenuItemBinding
import com.example.getcoffee.domain.models.MenuItem

class MenuAdapter(
    private val context: Context,
    private val onAddClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
    private val onRemoveClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
) : ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(DiffCallBack) {

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: MenuItem,
            onAddClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
            onRemoveClickListener: (menuItem: MenuItem, count: Int, position: Int) -> Unit,
            position: Int
        ) {
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
            binding.tvProductName.text = item.name
            binding.tvProductPrice.text = context.getString(R.string.price,item.price)
            binding.tvProductCount.text = item.count.toString()

            Glide.with(binding.root)
                .load(item.imageURL)
                .placeholder(R.drawable.coffee)
                .centerCrop()
                .into(binding.ivCoffee)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemBinding.inflate(inflater,parent,false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onAddClickListener, onRemoveClickListener, position)
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