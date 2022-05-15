package com.hadirahimi.movieapppowerfull.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.movieapppowerfull.models.home.ResponseMoviesList
import com.hadirahimi.movieapppowerfull.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository : SearchRepository) : ViewModel()
{
    val searchResult = MutableLiveData<ResponseMoviesList>()
    val loading = MutableLiveData<Boolean>()
    val empty = MutableLiveData<Boolean>()
    
    fun searchByName(name : String)
    {
        viewModelScope.launch {
            val response = repository.searchByName(name)
            loading.postValue(true)
            if (response.isSuccessful)
            {
                if (response.body()?.data !!.isNotEmpty())
                {
                    empty.postValue(false)
                    searchResult.postValue(response.body())
                }
                else empty.postValue(true)
                
            }
            loading.postValue(false)
        }
    }
}