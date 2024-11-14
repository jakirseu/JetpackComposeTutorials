package me.jakir.simplelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.simplelist.ui.theme.SimpleListTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NamesList()
                }
            }
        }
    }
}

@Composable
fun NamesList() {
    val names = listOf("Alice", "Bob", "Charlie")

    LazyColumn {
        items(names) { name ->
            Text(text = name)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NamesList()
}