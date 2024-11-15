package me.jakir.imageload

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import me.jakir.imageload.ui.theme.ImageLoadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageLoadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

                        Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                        // Load from Assets
                    LoadImageFromAssets(
                        assetName = "android.jpg",
                        context =  LocalContext.current
                    )
                        // Load from URL using Coil
                        LoadImageFromURL()
                        // Image with loading
                        LoadImageWithProgress()


                    }
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

@Composable
fun LoadImageFromAssets(context: Context, assetName: String) {
    // Access the AssetManager to load the file
    val assetManager = context.assets
    val inputStream = assetManager.open(assetName)
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Display the bitmap in an Image composable
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "Image from assets",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun LoadImageFromURL() {
    // Simplest way to load an image using Coil
    // You need to add Internet permission
    AsyncImage(
        model = "https://picsum.photos/200", // Replace with your image URL
        contentDescription = "Sample Image",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun LoadImageWithProgress() {
    // Use `SubcomposeAsyncImage` for more flexibility, e.g., progress indicators
    SubcomposeAsyncImage(
        model = "https://via.placeholder.com/150",
        contentDescription = "Image with Progress",
        modifier = Modifier.size(150.dp)
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator() // Show progress indicator while loading
        } else {
            SubcomposeAsyncImageContent() // Show the image once loaded
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageLoadTheme {
        Greeting("Android")
    }
}