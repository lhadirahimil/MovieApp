package com.hadirahimi.movieapppowerfull.repository

import com.hadirahimi.movieapppowerfull.api.ApiServices
import com.hadirahimi.movieapppowerfull.models.register.BodyRegister
import javax.inject.Inject

class RepositoryRegister @Inject constructor(private  val apiServices : ApiServices)
{
    suspend fun registerUser(bodyRegister : BodyRegister) = apiServices.registerUser(bodyRegister)
    
    
}