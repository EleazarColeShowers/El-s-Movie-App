package com.example.elsmovieapp.authUI

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.elsmovieapp.HomeActivity
import com.example.elsmovieapp.R
import com.example.elsmovieapp.Splash
import com.example.elsmovieapp.components.AppTextField
import com.example.elsmovieapp.components.MainButton
import com.example.elsmovieapp.data.AuthViewModel
import com.example.elsmovieapp.data.AuthViewModelFactory
import com.example.elsmovieapp.data.repository.AuthRepository
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme

class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create repository and ViewModel
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
                    Login(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hi!",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Welcome back! Please enter your details",
            fontSize = 15.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(64.dp))
        AppTextField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )
        Spacer(Modifier.height(24.dp))
        AppTextField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            keyboardType = KeyboardType.Password
        )
        Spacer(Modifier.height(50.dp))
        MainButton(
            text = "Login",
            onClick = {
                viewModel.login(email, password)
                context.startActivity(
                    Intent(
                        context,
                        HomeActivity::class.java
                    )
                )
            }
        )
    }
}