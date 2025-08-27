package com.example.elsmovieapp.data.repository

import com.example.elsmovieapp.data.remote.ApiClient

class MovieRepository {
    private val api = ApiClient.apiService

    suspend fun getPopularMovies(page: Int) = api.getPopularMovies(page)
}