package com.example.elsmovieapp.data.repository

import com.example.elsmovieapp.data.remote.ApiClient

class MovieRepository {
    private val api = ApiClient.apiService

    suspend fun getPopularMovies(page: Int) = api.getPopularMovies(page)

    suspend fun getNowPlayingMovies(page: Int) = api.getNowPlayingMovies(page)

    suspend fun getTopRated(page: Int) = api.getTopRated(page)

    suspend fun getUpcoming(page: Int) = api.getUpcoming(page)
}