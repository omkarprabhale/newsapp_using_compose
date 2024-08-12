package com.example.newsapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.Articles
import com.example.example.News
import com.example.newsapp.NewsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewModel : ViewModel() {
    private var news: MutableStateFlow<List<Articles>> = MutableStateFlow(listOf())
    val TAG = this.javaClass.toString()
    var newsState = news.asStateFlow()

    init {
        getNewsData()
    }

    fun getNewsData() {
        viewModelScope.launch {
            Log.d(TAG, "Omkar")
            val newsService = NewsService.newsInterface.getHeadlines("in", 1)
            newsService.enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.body() != null && response.isSuccessful) {
                        Log.d("omkar", "data in")
                        news.value = response.body()!!.articles
                    }

                }
                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d(TAG, "Error got while fetching", t)
                }
            })
        }
    }

}