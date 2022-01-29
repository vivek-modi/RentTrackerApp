package com.example.renttrackerapp.model

data class Place(
    var distance: Map<String, WayToGo>? = null,
    var lat: Double? = null,
    var long: Double? = null,
    var name: String? = null,
    var navigationLink: String? = null
)