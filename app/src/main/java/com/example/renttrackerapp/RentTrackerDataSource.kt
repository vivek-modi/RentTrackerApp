package com.example.renttrackerapp

import com.example.renttrackerapp.model.HomeResults

class RentTrackerDataSource {

    suspend fun getHomeRequest(pageNumber: Int): HomeResults {
        return ApiInterface.create().getHomeResults(pageNumber)
    }
}