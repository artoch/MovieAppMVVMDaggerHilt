package com.example.movieappmvvmdaggerhilt.data.remote.paged_factory

import androidx.paging.DataSource
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.data.remote.source.MoviesDataSource
import javax.inject.Inject

class PagedMovieFactory @Inject constructor(private val moviesDataSource: MoviesDataSource): DataSource.Factory<Int, ResultClass>()  {
    override fun create(): DataSource<Int, ResultClass> {
        return moviesDataSource
    }
}