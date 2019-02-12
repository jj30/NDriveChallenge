package ndrive.jj.bldg5.ndrive

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ndrive.jj.bldg5.ndrive.network.APIService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        srchView.queryHint = "Search Movies"

        val api_key = this.resources.getString(R.string.API_KEY)
        val tmdbAPI: APIService = APIService.createAPIService()

        srchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                tmdbAPI.search(query, api_key)
                        .subscribeOn(Schedulers.io())
                        ?.subscribe({
                            if (BuildConfig.DEBUG)
                                Log.i(javaClass.name, "${it.total_pages} pages of results were found.")

                            display(it)
                        }, {
                            Log.e(javaClass.name, it.message)
                        })

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        movies_recycler.layoutManager = LinearLayoutManager(this)

        // This is a stub. The notifyDataSetChanged passes the data.
        movies_recycler.adapter = TMDBAdapter(emptyList(), object: MoviesRecyclerClickListener { override fun onPositionClicked(position: Int) { } }, this)
    }

    private fun display(searchResults: SearchResults) {
        this.runOnUiThread {
            movies_recycler.layoutManager = LinearLayoutManager(this)

            // real data is in searchResults
            movies_recycler.adapter = TMDBAdapter(searchResults.results,
                    object: MoviesRecyclerClickListener {
                        override fun onPositionClicked(position: Int) {
                            loadMovie(searchResults.results[position])
                        }
                    },
                    this)

            (movies_recycler.adapter as TMDBAdapter).notifyDataSetChanged()
        }
    }

    private fun loadMovie(movie: Movie) {
        val singleMovie = Intent(this, SingleMovie::class.java)
        val bundle = Bundle()
        bundle.putString("movie", movieToJSON(movie))

        singleMovie.putExtras(bundle)
        startActivity(singleMovie)
    }

    private fun movieToJSON(movie: Movie): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<Movie>(Movie::class.java)
        val json = jsonAdapter.toJson(movie)

        if (BuildConfig.DEBUG)
            Log.i(javaClass.name, json)

        return json
    }
}


