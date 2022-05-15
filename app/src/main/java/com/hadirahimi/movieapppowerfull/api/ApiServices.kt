package com.hadirahimi.movieapppowerfull.api

import com.hadirahimi.movieapppowerfull.models.detail.ResponseMovieDetail
import com.hadirahimi.movieapppowerfull.models.home.ResponseGenreList
import com.hadirahimi.movieapppowerfull.models.home.ResponseMoviesList
import com.hadirahimi.movieapppowerfull.models.register.BodyRegister
import com.hadirahimi.movieapppowerfull.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.*

interface ApiServices
{
    @POST("register")
    suspend fun registerUser(@Body userData : BodyRegister) : Response<ResponseRegister>
    
    @GET("genres/{genre_id}/movies")
    suspend fun TopMovies(@Path("genre_id") id : Int) : Response<ResponseMoviesList>
    
    @GET("genres")
    suspend fun genreList() : Response<ResponseGenreList>
    
    
    @GET("movies")
    suspend fun lastMovies() : Response<ResponseMoviesList>
    
    @GET("movies")
    suspend fun searchByName(@Query("q") name : String) : Response<ResponseMoviesList>
    
    @GET("movies/{movie_id}")
    suspend fun movieInfo(@Path("movie_id") movieId : Int) : Response<ResponseMovieDetail>
}