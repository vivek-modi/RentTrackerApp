package com.example.renttrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.renttrackerapp.databinding.RentTrackerItemLayoutBinding
import com.example.renttrackerapp.modal.Home

class RentTrackerViewHolder(private val binding: RentTrackerItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun bindView(parent: ViewGroup): RentTrackerViewHolder {
            return RentTrackerViewHolder(
                RentTrackerItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bindItem(item: Home?) {
        binding.title.text = item?.title
    }
}