package com.example.elsmovieapp.ui.authUI


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
import androidx.compose.runtime.getValue
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
import com.example.elsmovieapp.ui.main.HomeActivity
import com.example.elsmovieapp.R
import com.example.elsmovieapp.ui.components.AppTextField
import com.example.elsmovieapp.ui.components.MainButton
import com.example.elsmovieapp.data.AuthViewModel
import com.example.elsmovieapp.data.AuthViewModelFactory
import com.example.elsmovieapp.data.repository.AuthRepository
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme

class SignUpActivity : ComponentActivity() {

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

                   SignUpPage(
                       modifier = Modifier
                         .padding(innerPadding)
                         .fillMaxSize(),
                       viewModel
                   )
                }
            }
        }
    }
}

@Composable
fun SignUpPage(modifier: Modifier, viewModel: AuthViewModel){
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context= LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Let's get started",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
            )

        Spacer(Modifier.height(8.dp))
        Text(
            text = "The latest movies and series are here",
            fontSize = 15.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(64.dp))
        AppTextField(
            label= "Full Name",
            value = fullName,
            onValueChange = { fullName = it },
            keyboardType = KeyboardType.Text
        )
        Spacer(Modifier.height(24.dp))
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
        MainButton(text = "Sign Up", onClick = {
            viewModel.signUp(email, password, fullName)
            context.startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                )
            )
        })

    }
}