package com.example.renttrackerapp.model

import java.io.Serializable

data class Place(
    var distance: Map<String, WayToGo>? = null,
    var lat: Double? = null,
    var long: Double? = null,
    var name: String? = null,
    var nameExtra: String? = null,
    var navigation_link: String? = null
) : Serializable