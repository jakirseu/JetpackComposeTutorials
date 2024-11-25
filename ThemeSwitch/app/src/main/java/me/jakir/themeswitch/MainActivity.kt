package me.jakir.themeswitch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.themeswitch.ui.theme.ThemeSwitchTheme
import android.content.Context

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Load the saved theme preference within a composable
            var isDarkTheme by remember { mutableStateOf(getThemePreference()) }

            ThemeSwitchTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Greeting(name = "Android")

                            // Toggle theme button
                            Button(onClick = {
                                isDarkTheme = !isDarkTheme
                                setThemePreference(isDarkTheme) // Save preference
                            }) {
                                Text("Toggle Theme")
                            }
                        }
                    }
                )
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
    ThemeSwitchTheme {
        Greeting("Android")
    }
}

private const val PREFS_NAME = "theme_prefs"
private const val PREF_DARK_THEME = "dark_theme"

fun Context.getThemePreference(): Boolean {
    val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(PREF_DARK_THEME, false) // Default to light theme
}

fun Context.setThemePreference(isDarkTheme: Boolean) {
    val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putBoolean(PREF_DARK_THEME, isDarkTheme).apply()
}
