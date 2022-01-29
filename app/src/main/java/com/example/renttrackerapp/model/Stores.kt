package com.example.renttrackerapp.model

data class Stores(
    var places: Map<String, Place>? = null,
    var stores: Map<String, Place>? = null
)