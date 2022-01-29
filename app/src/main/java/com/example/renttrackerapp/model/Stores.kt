package com.example.renttrackerapp.model

import java.io.Serializable

data class Stores(
    var places: Map<String, Place>? = null,
    var stores: Map<String, Place>? = null
) : Serializable