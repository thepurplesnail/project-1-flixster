package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TAG = "MainActivity"

// 1. Define a data model class as the data source [x]
// 2. Add the RecyclerView to the layout [x]
// 3. Create a custom row layout XML file to visualize the item [x]
// 4. Create an Adapter and ViewHolder to render the item [x]
// 5. Bind the adapter to the data source to populate the RecyclerView [x]
// 6. Bind a layout manager to the RecyclerView [x]

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // refer to recyclerview component
        rvMovies = findViewById(R.id.rvMovies)

        // create MovieAdapter instance to render movies list
        val movieAdapter = MovieAdapter(this, movies)

        // bind movieAdapter to recyclerview component
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()

        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) { Log.e(TAG, "onFailure $statusCode") }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON DATA $json")
                try {
                    // gets "results" json array from api
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    // add "results" json array to Movies mutable list instance movies
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    // update adapter if data fetched has changed
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list: $movies")
                } catch (e: JSONException){
                    Log.e(TAG, "Encountered exception: $e")
                }
            }
        })
    }
}



