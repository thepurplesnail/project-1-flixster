package com.example.flixster

import org.json.JSONArray

// Movie data class for storing each item from input JSON array
data class Movie (
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String
) {
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"

    companion object {
        // loops through input JSON array to instantiate each item as a
        // Movie instance and add to mutableList of Movies movies
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()

            for (i in 0 until movieJsonArray.length()){
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}