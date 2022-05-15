package com.hadirahimi.movieapppowerfull.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import com.hadirahimi.movieapppowerfull.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val repository : FavoriteRepository) : ViewModel()
{
    val favoriteList = MutableLiveData<List<MovieEntity>>()
    val empty = MutableLiveData<Boolean>()
    
    fun loadFavoriteMovie()
    {
        viewModelScope.launch {
            val list = repository.allFavoriteList()
            if (list.isNotEmpty())
            {
                empty.postValue(false)
                favoriteList.postValue(list)
                
            }
            else empty.postValue(true)
        }
    }
}