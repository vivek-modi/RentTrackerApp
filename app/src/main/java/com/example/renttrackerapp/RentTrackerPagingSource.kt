package com.example.renttrackerapp

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.renttrackerapp.modal.Home
import java.io.IOException

class RentTrackerPagingSource(
    private val rentTrackerDataSource: RentTrackerDataSource
) : PagingSource<Int, Home>() {

    override fun getRefreshKey(state: PagingState<Int, Home>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Home> {
        val key = params.key ?: 1
        return try {
            val data = rentTrackerDataSource.getHomeRequest(key)
            LoadResult.Page(
                data = data.results,
                prevKey = null,
                nextKey = if (data.count == 10) {
                    key.plus(1)
                } else {
                    null
                }
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}