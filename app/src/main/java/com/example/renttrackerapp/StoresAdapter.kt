package com.example.renttrackerapp

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.renttrackerapp.model.Place

class StoresAdapter : ListAdapter<Place, StoreViewHolder>(STORE_COMPARATOR) {

    companion object {
        private val STORE_COMPARATOR = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder.bindView(parent)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}