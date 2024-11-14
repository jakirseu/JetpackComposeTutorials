package me.jakir.navigationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.jakir.navigationdemo.ui.theme.NavigationDemoTheme
import androidx.navigation.NavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationDemoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        Modifier.padding(innerPadding)
                    ) {
                        composable("main") { MainScreen(navController) }
                        composable("detail") { DetailScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Button(onClick = { navController.navigate("detail") }) {
        Text("Go to Detail")
    }
}

@Composable
fun DetailScreen() {
    Text("Detail Screen")
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()  // Dummy navController for preview
    NavigationDemoTheme {
        MainScreen(navController)
    }
}
