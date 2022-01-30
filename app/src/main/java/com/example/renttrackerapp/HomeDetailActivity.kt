package com.example.renttrackerapp

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.renttrackerapp.databinding.ActivityHomeDetailBinding
import com.example.renttrackerapp.model.Home
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeDetailBinding
    private var storeAdapter = StoresAdapter()
    private var placeAdapter = StoresAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = intent.getSerializableExtra("itemDetail") as? Home

        supportActionBar?.title = home?.latitude?.let { getAddress(it, home.longitude) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.titleTextView.text = home?.title
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { mapGoogle ->
            home?.latitude?.toDouble()?.let { lat ->
                val location = LatLng(lat, home.longitude.toDouble())
                mapGoogle.addMarker(MarkerOptions().position(location).title("House Location"))
                mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f))
            }
        }
        binding.phoneTextView.apply {
            text = home?.phone
            setOnClickListener {
                home?.phone?.let { it1 -> callPhone(it1) }
            }
        }
        binding.daftLink.setOnClickListener {
            home?.link?.let { it1 -> openApp(it1) }
        }
        binding.propertyTextView.text = home?.prop_overview
        binding.rentTextView.text = home?.rent
        binding.descriptionTextView.text = home?.description
        binding.facilitiesTextView.text = home?.facilities?.joinToString("\n")
        binding.storeRecyclerView.adapter = storeAdapter
        binding.placeRecyclerView.adapter = placeAdapter

        home?.stores?.places?.forEach { (key, value) ->
            value.nameExtra = key
        }

        home?.stores?.stores?.forEach { (key, value) ->
            value.nameExtra = key
        }

        storeAdapter.submitList(home?.stores?.stores?.map {
            it.value
        })
        placeAdapter.submitList(home?.stores?.places?.map {
            it.value
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun callPhone(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }

    private fun openApp(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

    private fun getAddress(latitude: String, longitude: String): String? {
        val geocoder = Geocoder(binding.root.context, Locale.getDefault())
        var addresses: MutableList<Address>? = null
        try {
            addresses = geocoder.getFromLocation(
                latitude.toDouble(),
                longitude.toDouble(),
                1
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addresses?.get(0)?.locality
    }
}