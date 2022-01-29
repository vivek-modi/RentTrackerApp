package com.example.renttrackerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.renttrackerapp.databinding.ActivityHomeDetailBinding
import com.example.renttrackerapp.model.Home
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val home = intent.getSerializableExtra("itemDetail") as? Home

        lifecycleScope.launchWhenResumed {
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync { mapGoogle ->
                // Add a marker in Sydney and move the camera
                home?.latitude?.toDouble()?.let { lat ->
                    val location = LatLng(lat, home.longitude.toDouble())
                    mapGoogle.addMarker(MarkerOptions().position(location).title("House Location"))
                    mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f))
                }

            }
        }
    }
}