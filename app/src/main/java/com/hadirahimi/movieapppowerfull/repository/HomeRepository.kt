package com.hadirahimi.movieapppowerfull.repository

import com.hadirahimi.movieapppowerfull.api.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices : ApiServices)
{
    suspend fun topMoviesList(genreID : Int) = apiServices.TopMovies(genreID)
    
    suspend fun genresList() = apiServices.genreList()
    
    suspend fun lastMovies() = apiServices.lastMovies()
    
}