package com.peter.news.network
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.news.pojo.Article
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    fun getNewsAsync(@Query("page") page: Int, @Query(value = "q") query: String,@Query(value = "pageSize") pageSize : Int, @Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>

    @GET("v2/everything")
    fun getFilteredNewsAsync(@Query("q") query: String, @Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>

    @GET("v2/top-headlines")
    fun getCategorizedNewsAsync(@Query("category") category: String, @Query("apiKey") apikey: String):
            Deferred<NewsJsonNestedResponse>


}

enum class ApiStatus { LOADING, ERROR, DONE, EMPTY }

data class NewsJsonNestedResponse(var articles :List<Article>,
                              var status:String,var totalResults : Int)