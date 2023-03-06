package com.example.login

import androidx.paging.PagingSource
import androidx.paging.PagingState

class paging(val discer: discer2) : PagingSource<Int, Movie.Result>() {
    override fun getRefreshKey(state: PagingState<Int, Movie.Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult.Page<Int, Movie.Result> {

            var position = params.key ?: 1
            var response = discer.getMovies(position)
            return LoadResult.Page(
                data = response.results!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total_pages) null else position + 1
            )

    }
}
