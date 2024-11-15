package me.jakir.conditionalviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.conditionalviews.ui.theme.ConditionalViewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConditionalViewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.fillMaxSize(),Arrangement.Center, Alignment.CenterHorizontally) {

                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        ConditionalTextView()
                    }
                }
            }
        }
    }
}

@Composable
fun ConditionalTextView() {
    var showFirstText by remember { mutableStateOf(true) }

    Column {
        if (showFirstText) {
            Text("Hello, Jetpack Compose!")
        } else {
            Text("Goodbye, Jetpack Compose!")
        }
        Button(onClick = { showFirstText = !showFirstText }) {
            Text("Toggle")
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
    ConditionalViewsTheme {
        Greeting("Android")
    }
}