package ndrive.jj.bldg5.ndrive

import com.squareup.moshi.Json


data class Movie(
        @field:Json(name="vote_count") var vote_count: Int,
        @field:Json(name="id") var id: Int,
        @field:Json(name="video") var video: Boolean,
        @field:Json(name="title") var title: String,
        @field:Json(name="popularity") var popularity: Double,
        @field:Json(name="poster_path") var poster_path: String,
        @field:Json(name="original_language") var original_language: String,
        @field:Json(name="original_title") var original_title: String,
        @field:Json(name="genre_ids") var genre_ids: List<Int>,
        @field:Json(name="backdrop_path") var backdrop_path: String,
        @field:Json(name="adult") var adult: Boolean,
        @field:Json(name="overview") var overview: String,
        @field:Json(name="release_date") var release_date: String
)