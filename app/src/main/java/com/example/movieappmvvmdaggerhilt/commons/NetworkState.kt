package com.example.movieappmvvmdaggerhilt.commons

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED,
    END_OF_LIST
}

class NetworkState(val status:Status, val msg:String) {

    companion object{
        val LOADED:NetworkState
        val LOADING:NetworkState
        val ERROR:NetworkState
        val END_OF_LIST:NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS,"Success")
            LOADING = NetworkState(Status.RUNNING,"Running")
            ERROR = NetworkState(Status.FAILED,"Failed")
            END_OF_LIST = NetworkState(Status.END_OF_LIST,"End of list")
        }
    }
}