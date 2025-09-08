package com.example.elsmovieapp.data.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val adult: Boolean,
    val video: Boolean,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int>,
    val runtime: Int? = null,
    val cast: List<CastMember> = emptyList()
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?
)


data class CreditsResponse(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)

data class CrewMember(
    val id: Int,
    val name: String,
    val job: String,
    val department: String,
    val profile_path: String?
)
