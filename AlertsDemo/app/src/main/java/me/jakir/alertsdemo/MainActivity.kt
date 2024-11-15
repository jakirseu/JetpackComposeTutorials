package me.jakir.alertsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.jakir.alertsdemo.ui.theme.AlertsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlertsDemoTheme {

                Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

                    AlertView()
                }
            }
        }
    }
}

@Composable
fun AlertView() {
    var showAlert by remember { mutableStateOf(false) }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text("Alert") },
            text = { Text("This is an alert message.") },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("OK")
                }
            }
        )
    }

    Button(onClick = { showAlert = true }) {
        Text("Show Alert")
    }
}



