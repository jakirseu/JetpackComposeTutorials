package me.jakir.scrolldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.jakir.scrolldemo.ui.theme.ScrollDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScrollDemoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalScrollView()
                        VerticalScrollView()
                    }
                }
            }
        }
    }
}

@Composable
fun VerticalScrollView() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp) // Optional extra padding inside the scroll view
    ) {
        for (index in 1..100) {
            Text(
                text = "Item $index",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun HorizontalScrollView() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(16.dp) // Optional extra padding inside the scroll view
    ) {
        for (index in 1..10) {
            Text(
                text = "Item $index",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
