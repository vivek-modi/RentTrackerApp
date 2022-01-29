package com.example.renttrackerapp.model

import java.io.Serializable

data class Home(
    var description: String? = null,
    var facilities: List<String>? = null,
    var id: Int? = null,
    var latitude: String,
    var lease: String? = null,
    var link: String? = null,
    var longitude: String,
    var phone: String? = null,
    var rent: String? = null,
    var stores: Stores? = Stores(),
    var title: String? = null
) : Serializable