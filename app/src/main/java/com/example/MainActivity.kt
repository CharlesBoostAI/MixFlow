package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  private val playlistViewModel: PlaylistViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          var showSplash by remember { mutableStateOf(true) }

          Crossfade(
            targetState = showSplash,
            animationSpec = tween(durationMillis = 800),
            label = "splash_crossfade"
          ) { isSplashActive ->
            if (isSplashActive) {
              SplashScreen(onSplashFinished = { showSplash = false })
            } else {
              PlaylistGeneratorScreen(viewModel = playlistViewModel)
            }
          }
        }
      }
    }
  }
}
