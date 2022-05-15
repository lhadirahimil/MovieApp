package com.hadirahimi.movieapppowerfull.ui.register

import com.hadirahimi.movieapppowerfull.models.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule
{
    @Provides
    @Singleton
    fun provideUserBody() = BodyRegister()
}