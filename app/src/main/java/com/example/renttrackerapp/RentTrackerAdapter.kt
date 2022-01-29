package com.example.renttrackerapp

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.renttrackerapp.model.Home

class RentTrackerAdapter(
    private val itemClickListener: ItemClickListener
) : PagingDataAdapter<Home, RentTrackerViewHolder>(RESULT_COMPARATOR) {

    companion object {
        private val RESULT_COMPARATOR = object : DiffUtil.ItemCallback<Home>() {
            override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: RentTrackerViewHolder, position: Int) {
        holder.bindItem(getItem(position), itemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentTrackerViewHolder {
        return RentTrackerViewHolder.bindView(parent)
    }
}