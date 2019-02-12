package ndrive.jj.bldg5.ndrive

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_movie.*


class SingleMovie: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val json = intent.extras["movie"].toString()
        val movie = movieFromJSON(json)

        if (BuildConfig.DEBUG)
            Log.i(javaClass.name, movie.title)

        val backdrop_path = movie.backdrop_path ?: ""
        // Download image using picasso library
        if (backdrop_path.isNotEmpty()) {
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/$backdrop_path")
            Picasso.with(this).load(uri)
                    .into(imageView)
        }

        textTitle.text = movie.title
        textOverview.text = movie.overview
        textPopularity.text = "Average Rating: " + movie.popularity.toString()
        textVoteCount.text = "Vote Count: " + movie.vote_count.toString()
    }

    private fun movieFromJSON(json: String): Movie {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Movie>(Movie::class.java)

        return jsonAdapter.fromJson(json)
    }
}