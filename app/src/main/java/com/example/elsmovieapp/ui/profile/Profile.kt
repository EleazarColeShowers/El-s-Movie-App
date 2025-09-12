package com.example.elsmovieapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.elsmovieapp.R
import com.example.elsmovieapp.data.repository.MovieRepository
import com.example.elsmovieapp.ui.authUI.LogInActivity
import com.example.elsmovieapp.ui.authUI.Login
import com.example.elsmovieapp.ui.components.BottomBar
import com.example.elsmovieapp.ui.components.MainButton
import com.example.elsmovieapp.ui.search.SearchPage
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme
import com.example.elsmovieapp.ui.viewmodel.MovieViewModel
import com.example.elsmovieapp.ui.viewmodel.MovieViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class Profile : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MovieRepository()
        val viewModel: MovieViewModel = ViewModelProvider(
            this,
            MovieViewModelFactory(repository)
        )[MovieViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            ElsMovieAppTheme {
                val dark = colorResource(id = R.color.dark)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = dark,
                    bottomBar = { BottomBar(currentScreen = "Profile") }
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.homeimage),
                            contentDescription = "Home Image",
                            modifier = Modifier
                                .size(280.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(
                            text = "Log Out",
                            onClick = {
                                FirebaseAuth.getInstance().signOut()

                                val intent = Intent(this@Profile, LogInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}

