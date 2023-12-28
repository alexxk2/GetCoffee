package com.example.getcoffee.presentation.coffeeshops.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.getcoffee.databinding.CoffeShopItemBinding
import com.example.getcoffee.domain.models.Location

class CoffeeShopsAdapter(
    private val onItemClickListener: (item: Location) -> Unit
) : ListAdapter<Location, CoffeeShopsAdapter.CoffeeShopsViewHolder>(DiffCallBack) {

    class CoffeeShopsViewHolder(val binding: CoffeShopItemBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(item: Location, onItemClickListener: (item: Location) -> Unit){
                binding.tvShopName.text = item.name
                binding.root.setOnClickListener { onItemClickListener(item) }
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoffeeShopsAdapter.CoffeeShopsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =CoffeShopItemBinding.inflate(inflater,parent,false)
        return CoffeeShopsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoffeeShopsAdapter.CoffeeShopsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClickListener)
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Location>() {

            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }
        }
    }
}