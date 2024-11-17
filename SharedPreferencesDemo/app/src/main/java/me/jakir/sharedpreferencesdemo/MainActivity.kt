package me.jakir.sharedpreferencesdemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.sharedpreferencesdemo.ui.theme.SharedPreferencesDemoTheme
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedPreferencesDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (Modifier.fillMaxSize().padding(innerPadding)){
                        UserPreferencesScreen()
                        IDStorageScreen()
                    }

                }
            }
        }
    }
}


@Composable
fun UserPreferencesScreen() {
    val context = LocalContext.current
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var savedUserName by remember { mutableStateOf("") }

    // Load saved user name when the composable first starts
    LaunchedEffect(Unit) {
        savedUserName = getUserName(context) ?: ""
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Enter Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            saveUserName(context, userName.text)
            savedUserName = userName.text
        }) {
            Text("Save Username")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (savedUserName.isNotEmpty()) {
            Text("Saved Username: $savedUserName")
        }
    }
}

fun saveUserName(context: Context, userName: String) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("user_name", userName)
        apply()
    }
}



fun getUserName(context: Context): String? {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPref.getString("user_name", null)
}


// another example. without creating separate function
@Composable
fun IDStorageScreen() {
    val context = LocalContext.current
    var inputId by remember { mutableStateOf("") }
    var savedId by remember { mutableStateOf<Int?>(null) }

    // Load saved ID when the composable first starts
    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        savedId = sharedPref.getInt("user_id", -1).takeIf { it != -1 }
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputId,
            onValueChange = { inputId = it },
            label = { Text("Enter ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().putInt("user_id", inputId.toIntOrNull() ?: -1).apply()
            savedId = inputId.toIntOrNull()
        }) {
            Text("Save ID")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (savedId != null) {
            Text("Saved ID: $savedId")
        } else {
            Text("No ID saved")
        }
    }
}
