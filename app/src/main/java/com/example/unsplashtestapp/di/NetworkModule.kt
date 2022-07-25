package com.example.unsplashtestapp.di

import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.data.repositories.MainRepositoryImpl
import com.example.unsplashtestapp.domain.repositories.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideQuotesRepository(retrofitClient: RetrofitClient): MainRepository{
        return MainRepositoryImpl(retrofitClient)
    }

    @Provides
    fun provideRetrofitClient(): RetrofitClient{
        return RetrofitClient()
    }
}