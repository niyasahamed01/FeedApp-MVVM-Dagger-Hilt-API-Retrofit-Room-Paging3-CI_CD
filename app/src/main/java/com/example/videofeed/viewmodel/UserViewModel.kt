package com.example.videofeed.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.videofeed.api.ApiService
import com.example.videofeed.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel
@Inject
constructor(
    private val apiService: ApiService
) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 6)) {
        UserPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)


}
























