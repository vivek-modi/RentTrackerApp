package com.example.renttrackerapp.modal

data class HomeResults(
    var count: Int? = null,
    var results: List<Result>? = null,
    var success: Boolean? = null
)