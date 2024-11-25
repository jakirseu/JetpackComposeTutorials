package me.jakir.backgroundimage

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.jakir.backgroundimage.ui.theme.BackgroundImageTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundImageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // from drawable folder
                  //   BackgroundImageFromDrawable()

                    // From Assets Folder
                    BackgroundImageFromAssets(context = LocalContext.current, assetName = "bg.jpg")
                }
            }
        }
    }
}

@Composable
fun BackgroundImageFromDrawable() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.snow),
                contentScale = ContentScale.Crop
            )
    ) {
        Text("Overlay Text", color = Color.White)
    }
}


@Composable
fun BackgroundImageFromAssets(context: Context, assetName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize() // Fills the screen
    ) {
        // Background Image
        val assetManager = context.assets
        val inputStream = assetManager.open(assetName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Image from assets",
            modifier = Modifier
                .fillMaxSize() // Makes the image fill the entire Box
                .alpha(0.8f) // Optional: Adjust transparency
        )

        // Overlay Text
        Text(
            text = "Overlay Text",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp) // Adds some padding around the text
        )
    }
}




