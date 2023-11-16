package com.example.videofeed.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videofeed.api.ApiService
import com.example.videofeed.model.UserData

class UserPagingSource
    (
    private val apiService: ApiService
) : PagingSource<Int, UserData>() {

    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getAllUser(currentLoadingPageKey)
            val responseData = mutableListOf<UserData>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}