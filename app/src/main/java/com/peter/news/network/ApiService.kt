package com.peter.news.network
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.news.pojo.Article
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("v2/top-headlines")
    fun getNews(@Query("country") country: String,@Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>

    @GET("v2/everything")
    fun getFilteredNews(@Query("q") query: String,@Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>

    @GET("v2/top-headlines")
    fun getCategorizedNews(@Query("category") category: String,@Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>

    object NewsApi {
        val retrofitService: ApiService by lazy {
            retrofit.create(ApiService::class.java)
        }
    }

}

data class NewsJsonNestedResponse(var articles :List<Article>,
                              var status:String,var totalResults : Int)