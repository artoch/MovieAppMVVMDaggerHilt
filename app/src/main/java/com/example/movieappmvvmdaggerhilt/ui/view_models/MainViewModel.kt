package com.example.movieappmvvmdaggerhilt.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.data.remote.models.SingleMovieClass
import com.example.movieappmvvmdaggerhilt.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel(){
    // Companies Data

    fun movieDetail(id:Int):LiveData<SingleMovieClass> {
        return movieRepository.getMovieDetail(id)
    }
    val networkState = movieRepository.getNetworkState()

    val topMovies = movieRepository.getTopMovies()

    fun searchMovie(query:String):LiveData<List<ResultClass>>{
        return movieRepository.getSearchMovie(query)
    }

    fun listIsEmpty():Boolean{
        return topMovies.value?.isEmpty()?:true
    }

}