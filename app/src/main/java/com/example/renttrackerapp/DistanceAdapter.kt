package com.example.renttrackerapp

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.renttrackerapp.model.WayToGo

class DistanceAdapter : ListAdapter<WayToGo, DistanceViewHolder>(WAY_TO_GO_COMPARATOR) {

    companion object {
        private val WAY_TO_GO_COMPARATOR = object : DiffUtil.ItemCallback<WayToGo>() {
            override fun areItemsTheSame(oldItem: WayToGo, newItem: WayToGo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: WayToGo, newItem: WayToGo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: DistanceViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistanceViewHolder {
        return DistanceViewHolder.bindView(parent)
    }
}