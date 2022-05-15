package com.hadirahimi.movieapppowerfull.repository

import com.hadirahimi.movieapppowerfull.api.ApiServices
import com.hadirahimi.movieapppowerfull.db.MovieDao
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import javax.inject.Inject

class DetailRepository @Inject constructor(val apiServices : ApiServices , val dao : MovieDao)
{
    //Api
    suspend fun movieInfo(movieId : Int) = apiServices.movieInfo(movieId)
    
    //Database
    suspend fun insertToFavorite(movie : MovieEntity) = dao.insertMovie(movie)
    suspend fun deleteFromFavorite(movie : MovieEntity) = dao.deleteMovie(movie)
    suspend fun existsInFavorite(id : Int) = dao.existsMovie(id)
}