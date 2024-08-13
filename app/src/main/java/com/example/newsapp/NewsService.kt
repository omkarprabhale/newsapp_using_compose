package com.example.newsapp

import com.example.example.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/";

/*
Each user should have a unique API key. To create one, log in at https://newsapi.org/.
*/
const val API_KEY = "";

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int): Call<News>

}

object NewsService {

    val newsInterface: NewsInterface

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        newsInterface = retrofit.create(NewsInterface::class.java)

    }
}