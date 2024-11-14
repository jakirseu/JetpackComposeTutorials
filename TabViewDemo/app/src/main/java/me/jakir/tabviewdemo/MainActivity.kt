// source: https://www.freecodecamp.org/news/tabs-in-jetpack-compose/

package me.jakir.tabviewdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.tabviewdemo.ui.theme.TabViewDemoTheme

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.padding

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TabViewDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TabScreen()
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
    TabViewDemoTheme {
        Greeting("Android")
    }
}

@Composable
fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Home", "About", "Settings", "More", "Something", "Everything")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(WindowInsets.systemBars.asPaddingValues())
    ){
        ScrollableTabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                            2 -> Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                            3 -> Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                            4 -> Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                            5 -> Icon(imageVector = Icons.Default.Star, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> Greeting(name = "Home")
            1 -> Greeting(name = "About")
            2 -> Greeting(name = "Settings")
            3 -> Greeting(name = "Private")
            4 -> Greeting(name = "Favorite")
            5 -> Greeting(name = "Review")
        }
    }
}