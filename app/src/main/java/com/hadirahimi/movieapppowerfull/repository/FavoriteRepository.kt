package com.hadirahimi.movieapppowerfull.repository

import com.hadirahimi.movieapppowerfull.db.MovieDao
import com.hadirahimi.movieapppowerfull.db.MovieDatabase
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import javax.inject.Inject

class FavoriteRepository @Inject constructor(val dao:MovieDao)
{
    //suspend fun insertToFavorite(movie:MovieEntity) = dao.insertMovie(movie)
    
    fun allFavoriteList() = dao.getAllMovies()
}