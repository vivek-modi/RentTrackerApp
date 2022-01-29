package com.example.renttrackerapp.modal

data class HomeResults(
    var count: Int,
    var results: List<Home>,
    var success: Boolean
)