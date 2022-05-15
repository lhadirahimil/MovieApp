package com.hadirahimi.movieapppowerfull.di

import android.content.Context
import androidx.room.Room
import com.hadirahimi.movieapppowerfull.db.MovieDatabase
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import com.hadirahimi.movieapppowerfull.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context : Context) = Room.databaseBuilder(context,MovieDatabase::class.java,Constants.MOVIE_DATABASE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    
    @Provides
    @Singleton
    fun provideDao(db:MovieDatabase) = db.MovieDao()
    
    @Provides
    @Singleton
    fun provideEntity() = MovieEntity()
}