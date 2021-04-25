package com.example.movieappmvvmdaggerhilt.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieappmvvmdaggerhilt.commons.NetworkState
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.data.remote.models.SingleMovieClass
import com.example.movieappmvvmdaggerhilt.data.remote.paged_factory.PagedMovieFactory
import com.example.movieappmvvmdaggerhilt.data.remote.source.MoviesDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val pagedMovieFactory: PagedMovieFactory,private val moviesDataSource: MoviesDataSource, private val config: PagedList.Config) {

    fun getMovieDetail(id:Int):LiveData<SingleMovieClass>{
        moviesDataSource.fetchMoviesDetails(id)
        return moviesDataSource.downloadMoviesDetailsResponse
    }

    fun getTopMovies(): LiveData<PagedList<ResultClass>>{
        return LivePagedListBuilder(pagedMovieFactory,config).build()
    }

    fun getSearchMovie(query:String):LiveData<List<ResultClass>>{
        moviesDataSource.searchMovieList(query)
        return moviesDataSource.searchMovieList
    }

    //NetWork

    fun getNetworkState(): LiveData<NetworkState> = moviesDataSource.networkState

}