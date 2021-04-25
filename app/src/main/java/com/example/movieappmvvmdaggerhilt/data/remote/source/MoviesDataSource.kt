package com.example.movieappmvvmdaggerhilt.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieappmvvmdaggerhilt.commons.Const.Companion.FIRTS_PAGE
import com.example.movieappmvvmdaggerhilt.commons.NetworkState
import com.example.movieappmvvmdaggerhilt.commons.loge
import com.example.movieappmvvmdaggerhilt.data.remote.api.MovieApi
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.data.remote.models.SingleMovieClass
import com.example.movieappmvvmdaggerhilt.data.remote.models.TopMoviesClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDataSource @Inject constructor(private val singleMovieService: MovieApi):
    PageKeyedDataSource<Int, ResultClass>() {

    private var page = FIRTS_PAGE

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get () = _networkState

    private val _downloadMoviesDetailsResponse = MutableLiveData<SingleMovieClass>()
    val downloadMoviesDetailsResponse: LiveData<SingleMovieClass>
        get () = _downloadMoviesDetailsResponse

    private val _searchMovieList = MutableLiveData<List<ResultClass>>()
    val searchMovieList: LiveData<List<ResultClass>>
        get () = _searchMovieList

    fun fetchMoviesDetails(id:Int){
        _networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            val response = singleMovieService.getMovieDetails(id)
            if (response.isSuccessful){
                if (response.body()!=null){
                    _networkState.postValue(NetworkState.LOADED)
                    val data = response.body()!!
                    _downloadMoviesDetailsResponse.postValue(data)
                }
            }else{
                loge("ERROR EN OBTENER TODOS LOS COMPANIES")
            }
        }
    }

    fun searchMovieList(query:String){

        GlobalScope.launch(Dispatchers.IO) {
            val response = singleMovieService.getSearchMovies(query)
            if (response.isSuccessful){
                if (response.body()!=null){
                    val data = response.body()!!
                    _searchMovieList.postValue(data.results)
                }
            }else{
                _networkState.postValue(NetworkState.ERROR)
                loge("ERROR EN OBTENER TODOS LOS COMPANIES")
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultClass>
    ) {
        _networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch(Dispatchers.IO) {
            val response = singleMovieService.getTopMovies(page)
            if (response.isSuccessful){
                if (response.body()!=null){
                    callback.onResult(response.body()!!.results,null ,page + 1)
                    _networkState.postValue(NetworkState.LOADED)
                }
            }else{
                _networkState.postValue(NetworkState.ERROR)
                loge("ERROR EN OBTENER TODOS LOS COMPANIES")
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultClass>) {
        _networkState.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {
            val response = singleMovieService.getTopMovies(params.key)
            if (response.isSuccessful){
                if (response.body()!=null){
                    if (response.body()!!.totalPages >= params.key){
                        callback.onResult(response.body()!!.results,params.key + 1)
                        _networkState.postValue(NetworkState.LOADED)
                    }else{
                        _networkState.postValue(NetworkState.END_OF_LIST)
                    }
                    _networkState.postValue(NetworkState.LOADED)
                }
            }else{
                _networkState.postValue(NetworkState.ERROR)
                loge("ERROR EN OBTENER TODOS LOS COMPANIES")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultClass>) {

    }

}