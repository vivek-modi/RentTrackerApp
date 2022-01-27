package com.example.renttrackerapp.modal

data class Stores(
    var places: Map<String, Place>? = null,
    var stores: Map<String, Place>? = null
)