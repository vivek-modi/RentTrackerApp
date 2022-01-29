package com.example.renttrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.renttrackerapp.databinding.RentTrackerItemLayoutBinding
import com.example.renttrackerapp.model.Home

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

    fun bindItem(item: Home?, itemClickListener: ItemClickListener) {
        binding.titleText.text = item?.title
        binding.leaseText.text = item?.lease
        binding.root.setOnClickListener {
            if (item != null) {
                itemClickListener.onClick(item)
            }
        }
        binding.deleteIcon.setOnClickListener {
            item?.id?.let { it1 -> itemClickListener.onItemDelete(it1) }
        }
    }

//    private fun getAddress(latitude: String, longitude: String): String {
//        val geocoder = Geocoder(binding.root.context, Locale.getDefault())
//        var addresses: MutableList<Address>? = null
//        try {
//            addresses = geocoder.getFromLocation(
//                latitude.toDouble(),
//                longitude.toDouble(),
//                1
//            )
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return addresses?.get(0).toString()
//    }
}