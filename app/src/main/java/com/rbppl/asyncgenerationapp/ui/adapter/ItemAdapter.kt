package com.rbppl.asyncgenerationapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbppl.asyncgenerationapp.R
import com.rbppl.asyncgenerationapp.model.ItemModel


class ItemAdapter(private val onDeleteClickListener: (Int) -> Unit) :
    ListAdapter<ItemModel, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view, onDeleteClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ItemViewHolder(
        itemView: View,
        private val onDeleteClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val numberTextView: TextView = itemView.findViewById(R.id.numberTextView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(item: ItemModel, position: Int) {
            numberTextView.text = item.number.toString()
            deleteButton.setOnClickListener {
                onDeleteClickListener.invoke(position)
            }
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
}
