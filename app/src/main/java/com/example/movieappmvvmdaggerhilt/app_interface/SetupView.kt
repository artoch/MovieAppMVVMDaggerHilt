package com.example.movieappmvvmdaggerhilt.app_interface

interface SetupView {

    val TAG: String

    fun setupView(){
        setupBindings()
        setupObservers()
    }

    fun setupObservers()

    fun setupBindings()

}