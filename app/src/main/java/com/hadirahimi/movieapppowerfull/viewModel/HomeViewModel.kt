package com.hadirahimi.movieapppowerfull.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.movieapppowerfull.models.home.ResponseGenreList
import com.hadirahimi.movieapppowerfull.models.home.ResponseMoviesList
import com.hadirahimi.movieapppowerfull.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository : HomeRepository) : ViewModel()
{
    val topMovies = MutableLiveData<ResponseMoviesList>()
    val genresList = MutableLiveData<ResponseGenreList>()
    val lastMovies = MutableLiveData<ResponseMoviesList>()
    val loading = MutableLiveData<Boolean>()
    
    fun getTopMovies(id : Int) = viewModelScope.launch {
        val response = repository.topMoviesList(id)
        if (response.isSuccessful)
        {
            topMovies.postValue(response.body())
        }
    }
    
    fun genreList() = viewModelScope.launch {
        val response = repository.genresList()
        if (response.isSuccessful)
        {
            genresList.postValue(response.body())
        }
    }
    
    fun lastMovies() = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.lastMovies()
        if (response.isSuccessful)
        {
            lastMovies.postValue(response.body())
        }
        loading.postValue(false)
    }
    
    
}