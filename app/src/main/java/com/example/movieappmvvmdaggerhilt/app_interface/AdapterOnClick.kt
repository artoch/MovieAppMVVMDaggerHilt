package com.example.movieappmvvmdaggerhilt.app_interface

interface AdapterOnClick<T> {
    fun adapterOnClick(item:T, pos:Int = 0)
}