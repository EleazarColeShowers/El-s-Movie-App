package com.example.elsmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elsmovieapp.components.MainButton
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween

class Splash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElsMovieAppTheme {
                val dark = colorResource(id = R.color.dark)
                var showSplash by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(2000)
                    showSplash = false
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = dark
                ) { innerPadding ->
                    Crossfade(
                        targetState = showSplash,
                        animationSpec = tween(durationMillis = 1200)
                    ) { splashVisible ->
                        if (splashVisible) {
                            SplashScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            )
                        } else {
                            SplashPage()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SplashScreen(modifier: Modifier) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.homeimage),
            contentDescription = null,
            modifier = Modifier.size(350.dp)
        )
    }
}

@Composable
fun SplashPage(){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.homeimage),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Spacer(Modifier.height(51.dp))
        Text(
            text = "El's Movie App",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Enter your email to sign up",
            color = Color.LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(Modifier.height(64.dp))
        MainButton(text = "Sign Up", onClick = {})

        Spacer(Modifier.height(34.dp))
        Text(
            text = "I already have an account? Login",
            color = Color.LightGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}