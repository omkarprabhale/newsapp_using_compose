package com.example.newsapp

import android.app.Application


class NewsApplication : Application() {

    val TAG = this.javaClass.toString()


    override fun onCreate() {

        super.onCreate()

    }

    /*private fun newsData() {
        Log.d(TAG, "Omkar")
        val newsService = NewsService.newsInterface.getHeadlines("in", 1)
        newsService.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.body() != null && response.isSuccessful) {
                    newsData = response.body();
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d(TAG, "Error got while fetching", t)
            }


        })
    }*/

}