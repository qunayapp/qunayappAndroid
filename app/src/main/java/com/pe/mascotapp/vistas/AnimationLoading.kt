package com.pe.mascotapp.vistas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pe.mascotapp.R
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.vistas.ui.theme.MascotappTheme
import kotlinx.coroutines.delay

class AnimationLoading : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {

            SplashScreen {
                val pref = applicationContext.getSharedPreferences(
                    Constantes.SHARED_PREF,
                    Context.MODE_PRIVATE
                )
                val session = pref.getBoolean(Constantes.SHARED_PREF_SUCCESS, false)
                // Start the nextactivity and finish the splash screen
                if (session) {
                    val mensaje = pref.getString(Constantes.SHARED_PREF_MESSAGE, "")
                    Utils.dump("client_name: $mensaje")
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }
    }
}

@Composable
fun LottieExample(onAnimationFinished: () -> Unit) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.qunay_animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1 // Or a specific number of iterations
    )
    val isPlaying by remember { derivedStateOf { progress < 1f } } // Check if animation is in progress
    LottieAnimation(composition = composition, progress = progress)
   // Text("Animation is playing: $isPlaying")
    LaunchedEffect(key1 = isPlaying) {
        if (!isPlaying) {
            // Animation finished, perform your action here
            onAnimationFinished()
        }
    }
}

@Composable
fun SplashScreen(onAnimationFinished: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue_primary)), // Customize background color
        contentAlignment = Alignment.Center
    ) {
        LottieExample(onAnimationFinished)
    }
}

@Preview
@Composable
fun GreetingPreview() {
    MascotappTheme {
        LottieExample({})
    }
}