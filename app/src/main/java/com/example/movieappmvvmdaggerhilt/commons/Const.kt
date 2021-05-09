package com.example.movieappmvvmdaggerhilt.commons


class Const {

    companion object {

        val PREFERENCE_NAME = "USER_DATA"

        val DATABASE_NAME = "practiceApp"


        val API_KEY =  "YOUR_API_KEY"
        val API_URL = "https://api.themoviedb.org/3/"
        val BASE_API_URL = "https://image.tmdb.org/t/p/w92/"
        const val FIRTS_PAGE = 1
        const val POST_PER_PAGE = 10
    }

    class ConstPreference{
        companion object{
            //FOR RECYCLERVIEWS
            const val RECYCLERCOMPANIESLIST = "COMPANIESFRAGMENT"
        }
    }
}