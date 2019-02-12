package ndrive.jj.bldg5.ndrive.network

import io.reactivex.Observable
import ndrive.jj.bldg5.ndrive.BuildConfig
import ndrive.jj.bldg5.ndrive.SearchResults
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface APIService {
    @GET(url_search)
    fun search(
            @Query("query") id: String,
            @Query("api_key") api_key: String
    ): Observable<SearchResults>

    companion object {
        const val url_search = "/3/search/movie"

        fun createAPIService(): APIService {
            val API_URL = "https://api.themoviedb.org/"

            val httpClient = OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)

            if (BuildConfig.DEBUG) {
                val httpLoggingIC = HttpLoggingInterceptor()
                httpLoggingIC.level = HttpLoggingInterceptor.Level.BODY

                httpClient.addInterceptor(httpLoggingIC)
            }

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(API_URL)
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }
}