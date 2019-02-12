package ndrive.jj.bldg5.ndrive

import com.squareup.moshi.Json

data class SearchResults(
        @field:Json(name="page") var page: Int,
        @field:Json(name="total_results") var total_results: Int,
        @field:Json(name="total_pages") var total_pages: Int,
        @field:Json(name="results") var results: List<Movie>
)