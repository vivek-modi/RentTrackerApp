package com.example.renttrackerapp.model

data class HomeResults(
    var count: Int,
    var results: List<Home>,
    var success: Boolean
)