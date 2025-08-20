package com.example.elsmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.elsmovieapp.authUI.SignUpPage
import com.example.elsmovieapp.data.AuthViewModel
import com.example.elsmovieapp.data.AuthViewModelFactory
import com.example.elsmovieapp.data.repository.AuthRepository
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AuthRepository()
        val viewModel: AuthViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(repository)
        )[AuthViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            ElsMovieAppTheme {
                val dark = colorResource(id = R.color.dark)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = dark
                ) { innerPadding ->

                    HomePage(
                        modifier = Modifier.padding(innerPadding),
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    val username by viewModel.username.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserName()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello, ${username ?: ""}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Let's stream your favorite movies",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                )
            }

            Image(
                painter = painterResource(R.drawable.wishlist),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
