package com.example.elsmovieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elsmovieapp.data.model.CastMember
import com.example.elsmovieapp.data.model.Movie
import com.example.elsmovieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _nowPlaying = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlaying: StateFlow<List<Movie>> = _nowPlaying

    private val _topRated = MutableStateFlow<List<Movie>>(emptyList())
    val topRated: StateFlow<List<Movie>> = _topRated

    private val _upcoming = MutableStateFlow<List<Movie>>(emptyList())
    val upcoming: StateFlow<List<Movie>> = _upcoming

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _movieCast = MutableStateFlow<List<CastMember>>(emptyList())
    val movieCast: StateFlow<List<CastMember>> = _movieCast


    private var currentPage = 1
    private var nowPlayingPage = 1
    private var topRatedPage = 1
    private var upcomingPage = 1


    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies(currentPage)
                _movies.value = _movies.value + response.results
                currentPage++
                Log.d("MovieViewModel", "Fetched popular page $currentPage")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching popular movies", e)
            }
        }
    }

    fun fetchNowPlaying() {
        viewModelScope.launch {
            try {
                val response = repository.getNowPlayingMovies(nowPlayingPage)
                _nowPlaying.value = _nowPlaying.value + response.results
                nowPlayingPage++
                Log.d("MovieViewModel", "Fetched now playing page $nowPlayingPage")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching now playing movies", e)
            }
        }
    }
    fun fetchTopRated() {
        viewModelScope.launch {
            try {
                val response = repository.getTopRated(topRatedPage)
                _topRated.value = _topRated.value + response.results
                topRatedPage++
                Log.d("MovieViewModel", "Fetched top rated page $topRatedPage")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching top rated movies", e)
            }
        }
    }

    fun fetchUpcoming() {
        viewModelScope.launch {
            try {
                val response = repository.getUpcoming(upcomingPage)
                _upcoming.value = _upcoming.value + response.results
                upcomingPage++
                Log.d("MovieViewModel", "Fetched upcoming page $upcomingPage")
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching upcoming movies", e)
            }
        }
    }
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(query)
                _searchResults.value = response.results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }

    fun fetchCastDetails(movieId: Int) {
        viewModelScope.launch {
            val cast = repository.getMovieCredits(movieId)
            _movieCast.value = cast
        }
    }

}


class MovieViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
