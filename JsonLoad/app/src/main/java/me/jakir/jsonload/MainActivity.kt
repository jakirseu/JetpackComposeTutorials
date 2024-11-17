package me.jakir.jsonload

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.jsonload.ui.theme.JsonLoadTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.Json
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class News(
    val id: Int,
    val title: String,
    val news: String

)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JsonLoadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.fillMaxSize().padding(innerPadding)) {
                        NewsScreen()
                    }
                    }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JsonLoadTheme {
        Greeting("Android")
    }
}

// Composable function to display list of news
@Composable
fun NewsList(newsItems: List<News>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(newsItems) { newsItem ->
            NewsItemView(newsItem)
        }
    }
}

@Composable
fun NewsItemView(newsItem: News) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = newsItem.title, style = MaterialTheme.typography.titleMedium)
        Text(text = newsItem.news, style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun NewsScreen() {
    var newsItems by remember { mutableStateOf(emptyList<News>()) }

    LaunchedEffect(Unit) {
        newsItems = loadNewsData("https://jakir.me/mrt/news.json")
    }

    if (newsItems.isNotEmpty()) {
        NewsList(newsItems)
    } else {
        Text("Loading news...")
    }
}

suspend fun loadNewsData(url: String): List<News> = withContext(Dispatchers.IO) {
    val connection = URL(url).openConnection() as HttpURLConnection
    try {
        connection.inputStream.bufferedReader().use { reader ->
            val json = reader.readText()
            Log.d("tagData", json)
            Json.decodeFromString<List<News>>(json)
        }
    } finally {
        connection.disconnect()
    }
}
