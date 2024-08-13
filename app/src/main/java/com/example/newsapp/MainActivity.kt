package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.example.Articles
import com.example.newsapp.model.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.utils.Network


class MainActivity : ComponentActivity() {
    private lateinit var newsModel: NewsViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (Network.isNetworkAvailable(this)) {
                        newsModel = ViewModelProvider(this)[NewsViewModel::class.java]
                        Greeting(newsModel.newsState.collectAsState())
                    } else {
                        Nointernet()

                    }

                }
            }
        }

    }


    @Composable
    private fun Nointernet() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = "Network disabled. Please connect to the internet.",
                modifier = Modifier.wrapContentWidth().padding(15.dp),
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = Color.DarkGray,

            )
        }
    }

    override fun onResume() {
        super.onResume()
    }

    @Composable
    fun Greeting(news: State<List<Articles>>) {
        var loading by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = news.value.isNotEmpty())
        {
            loading = true
        }
        if (!loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.secondary,
                strokeWidth = 4.dp

            )
        }
        Column() {
            if (loading) {
                Text(
                    text = "TOP Headlines",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 35.sp

                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                if (news != null) {
                    items(news.value) { it ->
                        NewsCard(data = it)
                    }
                } else {
                    Log.d("omkar", "list is null")
                }

            }
        }

    }


    @Composable
    fun NewsCard(
        data: Articles
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    getDetailsOfNews(data)

                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )

        ) {
            Column() {
                Text(
                    text = data.title.toString(),
                    modifier = Modifier.padding(all = 5.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp
                )

                Row() {
                    Text(
                        text = data.author.toString(),
                        modifier = Modifier.padding(all = 5.dp),
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Blue,
                        fontSize = 10.sp
                    )

                    Text(
                        text = data.publishedAt.toString(),
                        modifier = Modifier.padding(all = 5.dp),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 10.sp
                    )
                }
            }
        }


    }

    private fun getDetailsOfNews(data: Articles) {

        val url = data.url
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)

    }

    @Preview
    @Composable
    private fun TextComposablePreview() {
        Nointernet()
    }
}



