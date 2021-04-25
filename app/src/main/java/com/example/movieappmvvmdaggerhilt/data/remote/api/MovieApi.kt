package com.example.movieappmvvmdaggerhilt.data.remote.api

import com.example.movieappmvvmdaggerhilt.data.remote.models.SingleMovieClass
import com.example.movieappmvvmdaggerhilt.data.remote.models.TopMoviesClass
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id:Int): Response<SingleMovieClass>

    @GET("movie/top_rated")
    suspend fun getTopMovies(@Query("page") page:Int): Response<TopMoviesClass>

    @GET("search/movie")
    suspend fun getSearchMovies(@Query("query") query:String): Response<TopMoviesClass>

}