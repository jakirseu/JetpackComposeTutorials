package me.jakir.simpleanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import me.jakir.simpleanimation.ui.theme.SimpleAnimationTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   AnimatedButton(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AnimatedButton(modifier: Modifier = Modifier.fillMaxSize()) {
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(targetValue = scale)
    Column(modifier = modifier) {
        Button(onClick = {
            scale = if (scale == 1f) 1.5f else 1f
        }) {
            Text("Press Me", modifier = Modifier.scale(animatedScale))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimatedButton()
}