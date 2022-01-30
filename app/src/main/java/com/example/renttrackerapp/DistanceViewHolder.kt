package com.example.renttrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.renttrackerapp.databinding.DistanceItemLayoutBinding
import com.example.renttrackerapp.model.WayToGo

class DistanceViewHolder(val binding: DistanceItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun bindView(parent: ViewGroup): DistanceViewHolder {
            return DistanceViewHolder(
                DistanceItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bindItem(item: WayToGo) {
        binding.distanceTextView.text = item.distance
        binding.timeTextView.text = item.time
        binding.typeTextView.text = item.name
    }
}