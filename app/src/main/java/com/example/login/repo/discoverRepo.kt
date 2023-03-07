package com.example.login.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.login.discer2
import com.example.login.paging
import javax.inject.Inject

class discoverRepo @Inject constructor(val discer: discer2) {
    fun getDiscy() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 30),
        pagingSourceFactory = {paging(discer)}
    ).liveData
}