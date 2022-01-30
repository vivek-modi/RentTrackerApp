package com.example.renttrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.renttrackerapp.databinding.StoreItemLayoutBinding
import com.example.renttrackerapp.model.Place


class StoreViewHolder(val binding: StoreItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val distanceAdapter = DistanceAdapter()

    companion object {
        fun bindView(parent: ViewGroup): StoreViewHolder {
            return StoreViewHolder(
                StoreItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bindItem(item: Place?) {
        binding.storeNameTextView.text = item?.nameExtra
        binding.distanceRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            this.adapter = distanceAdapter
        }

        item?.distance?.forEach { (key, value) ->
            value.name = key
        }

        distanceAdapter.submitList(item?.distance?.map {
            it.value
        })
    }
}