package com.example.renttrackerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
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

        binding.navigationLink.setOnClickListener {
            item?.navigation_link?.let { it1 -> openMap(it1) }
        }

        item?.distance?.forEach { (key, value) ->
            value.name = key
        }

        distanceAdapter.submitList(item?.distance?.map {
            it.value
        })
    }

    private fun openMap(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.google.android.apps.maps")
        intent.data = Uri.parse(link)
        startActivity(binding.root.context, intent, Bundle())
    }
}