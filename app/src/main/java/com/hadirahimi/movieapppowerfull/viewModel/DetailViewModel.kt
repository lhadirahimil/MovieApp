package com.hadirahimi.movieapppowerfull.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import com.hadirahimi.movieapppowerfull.models.detail.ResponseMovieDetail
import com.hadirahimi.movieapppowerfull.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository : DetailRepository) : ViewModel()
{
    var movieInfo = MutableLiveData<ResponseMovieDetail>()
    var loading = MutableLiveData<Boolean>()
    
    fun movieInfo(movieId : Int) = viewModelScope.launch {
        val response = repository.movieInfo(movieId)
        loading.postValue(true)
        if (response.isSuccessful)
        {
            movieInfo.postValue(response.body())
        }
        loading.postValue(false)
    }
    
    //database
    val isFavorite = MutableLiveData<Boolean>()
    
    suspend fun exists(id : Int) = withContext(viewModelScope.coroutineContext) {
        repository.existsInFavorite(id)
    }
    
    fun favoriteMovie(id : Int , entity : MovieEntity) = viewModelScope.launch {
        val exits = repository.existsInFavorite(id)
        if (exits)
        {
            isFavorite.postValue(false)
            repository.deleteFromFavorite(entity)
        }
        else
        {
            isFavorite.postValue(true)
            repository.insertToFavorite(entity)
        }
    }
    
}