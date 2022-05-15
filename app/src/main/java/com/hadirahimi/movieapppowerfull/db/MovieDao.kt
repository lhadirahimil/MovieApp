package com.hadirahimi.movieapppowerfull.db

import android.database.sqlite.SQLiteDatabase
import androidx.room.Dao
import androidx.room.Insert
import com.hadirahimi.movieapppowerfull.utils.Constants
import androidx.room.*
import retrofit2.http.Query

@Dao
interface MovieDao
{
    @Insert(onConflict = SQLiteDatabase.CONFLICT_REPLACE)
    suspend fun insertMovie(movie:MovieEntity)
    
    @Delete
    suspend fun deleteMovie(movie:MovieEntity)
    
    @androidx.room.Query("SELECT * FROM ${Constants.MOVIE_TABLE}")
    fun getAllMovies():MutableList<MovieEntity>
    
    @androidx.room.Query("SELECT EXISTS (SELECT 1 FROM ${Constants.MOVIE_TABLE} WHERE id = :id)")
    suspend fun existsMovie(id:Int):Boolean
    
    
}