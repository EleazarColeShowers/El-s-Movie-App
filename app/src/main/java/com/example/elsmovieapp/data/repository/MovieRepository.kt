package com.example.elsmovieapp.data.repository

import com.example.elsmovieapp.data.model.CastMember
import com.example.elsmovieapp.data.model.MovieResponse
import com.example.elsmovieapp.data.remote.ApiClient

class MovieRepository {
    private val api = ApiClient.apiService

    suspend fun getPopularMovies(page: Int) = api.getPopularMovies(page)

    suspend fun getNowPlayingMovies(page: Int) = api.getNowPlayingMovies(page)

    suspend fun getTopRated(page: Int) = api.getTopRated(page)

    suspend fun getUpcoming(page: Int) = api.getUpcoming(page)

    suspend fun searchMovies(query: String): MovieResponse {
        return api.searchMovies(query)
    }

    suspend fun getMovieCredits(movieId: Int): List<CastMember> {
        return api.getMovieCredits(movieId).cast
    }
}