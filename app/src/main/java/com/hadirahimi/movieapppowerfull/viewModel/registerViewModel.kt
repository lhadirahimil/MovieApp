package com.hadirahimi.movieapppowerfull.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadirahimi.movieapppowerfull.models.register.BodyRegister
import com.hadirahimi.movieapppowerfull.models.register.ResponseRegister
import com.hadirahimi.movieapppowerfull.repository.RepositoryRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class registerViewModel @Inject constructor (private val repository : RepositoryRegister) : ViewModel()
{
    val registerUser = MutableLiveData<ResponseRegister>()
    val loading = MutableLiveData<Boolean>()
    
    fun registerUser(body : BodyRegister) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.registerUser(body)
        if (response.isSuccessful)
        {
            registerUser.postValue(response.body())
        }
        loading.postValue(false)
    
    
    }
}