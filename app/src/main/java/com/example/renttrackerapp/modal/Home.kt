package com.example.renttrackerapp.modal

data class Home(
    var description: String? = null,
    var facilities: List<String>? = null,
    var id: Int? = null,
    var latitude: String? = null,
    var lease: String? = null,
    var link: String? = null,
    var longitude: String? = null,
    var phone: String? = null,
    var rent: String? = null,
    var stores: Stores? = Stores(),
    var title: String? = null
)