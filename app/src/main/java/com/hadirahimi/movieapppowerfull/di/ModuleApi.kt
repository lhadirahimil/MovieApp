package com.hadirahimi.movieapppowerfull.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hadirahimi.movieapppowerfull.api.ApiServices
import com.hadirahimi.movieapppowerfull.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object ModuleApi
{
    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL
    
    @Provides
    @Singleton
    fun provideConnectionTime() = Constants.CONNECTION_TIME
    
    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()
    
    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    @Provides
    @Singleton
    fun provideClient(time : Long , interceptor : HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor).writeTimeout(time , TimeUnit.SECONDS)
            .readTimeout(time , TimeUnit.SECONDS).connectTimeout(time , TimeUnit.SECONDS).build()
    
    
    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient , gson : Gson , baseUrl : String) : ApiServices =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build().create(ApiServices::class.java)
    
    
}

