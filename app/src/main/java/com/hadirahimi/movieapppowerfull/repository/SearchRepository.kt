package com.hadirahimi.movieapppowerfull.repository

import com.hadirahimi.movieapppowerfull.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(val apiServices : ApiServices)
{
    suspend fun searchByName(name : String) = apiServices.searchByName(name)
}