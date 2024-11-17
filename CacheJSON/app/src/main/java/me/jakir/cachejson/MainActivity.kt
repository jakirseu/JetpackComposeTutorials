package me.jakir.cachejson

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.jakir.cachejson.ui.theme.CacheJSONTheme
import java.net.HttpURLConnection
import java.net.URL

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.pullrefresh.PullRefreshIndicator


@Serializable
data class News(val id: Int, val title: String, val news: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CacheJSONTheme {
                Scaffold { innerPadding ->
                    NewsScreen(context = this@MainActivity, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(context: Context, modifier: Modifier = Modifier) {
    var newsItems by remember { mutableStateOf<List<News>?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        newsItems = loadJsonData(context, "news_data") ?: fetchAndCacheNews(context)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                newsItems = fetchAndCacheNews(context)
                isRefreshing = false
            }
        }
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(newsItems.orEmpty()) { newsItem ->
                NewsItemView(newsItem)
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun NewsItemView(newsItem: News) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = newsItem.title)
        Text(text = newsItem.news)
    }
}

suspend fun fetchAndCacheNews(context: Context): List<News> {
    val url = "https://jakir.me/mrt/news.json"
    val newsData = fetchNewsData(url)
    saveJsonData(context, "news_data", newsData)
    Toast.makeText(context, "Fetched news data.", Toast.LENGTH_SHORT).show()
    return newsData
}

suspend fun fetchNewsData(url: String): List<News> = withContext(Dispatchers.IO) {
    val connection = URL(url).openConnection() as HttpURLConnection
    try {
        connection.inputStream.bufferedReader().use { reader ->
            val json = reader.readText()
            Json.decodeFromString<List<News>>(json)
        }
    } finally {
        connection.disconnect()
    }
}

fun saveJsonData(context: Context, key: String, data: List<News>) {
    val sharedPreferences = context.getSharedPreferences("json_cache", Context.MODE_PRIVATE)
    val jsonData = Json.encodeToString<List<News>>(data)
    sharedPreferences.edit().putString(key, jsonData).apply()
}

fun loadJsonData(context: Context, key: String): List<News>? {
    val sharedPreferences = context.getSharedPreferences("json_cache", Context.MODE_PRIVATE)
    val jsonData = sharedPreferences.getString(key, null)
    return jsonData?.let { Json.decodeFromString<List<News>>(it) }
}
