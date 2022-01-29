package com.example.renttrackerapp.model

import java.io.Serializable

data class WayToGo(
    var distance: String? = null,
    var time: String? = null,
    var error: String? = null
) : Serializable