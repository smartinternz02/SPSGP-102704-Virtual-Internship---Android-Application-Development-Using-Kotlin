package com.lrm.grocerybasket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lrm.grocerybasket.data.GroceryItem
import com.lrm.grocerybasket.databinding.RvItemBinding

class GroceryListAdapter(private val onItemClicked: (GroceryItem) -> Unit) :
    ListAdapter<GroceryItem, GroceryListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroceryItem) {
            binding.apply {
                itemName.text = item.itemName
                itemQuantity.text = item.itemQuantity.toString()
                itemPrice.text = "₹ " + item.itemPrice.toString()

                val amount = item.itemQuantity * item.itemPrice
                itemAmount.text = "₹  " + amount.toString()
            }
        }
    }

        companion object {
            private val DiffCallback = object : DiffUtil.ItemCallback<GroceryItem>() {
                override fun areItemsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: GroceryItem, newItem: GroceryItem): Boolean {
                    return oldItem.itemName == newItem.itemName
                }
            }
        }
}