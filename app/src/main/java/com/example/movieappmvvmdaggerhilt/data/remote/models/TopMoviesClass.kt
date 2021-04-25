package com.example.movieappmvvmdaggerhilt.data.remote.models

import com.google.gson.annotations.SerializedName

data class TopMoviesClass (
    val page: Int,
    val results: List<ResultClass>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)