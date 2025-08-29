package com.example.elsmovieapp.ui.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModelProvider
import com.example.elsmovieapp.R
import com.example.elsmovieapp.data.repository.AuthRepository
import com.example.elsmovieapp.ui.main.HomePage
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme
import com.example.elsmovieapp.ui.viewmodel.AuthViewModel
import com.example.elsmovieapp.ui.viewmodel.AuthViewModelFactory

class Search  : ComponentActivity() {

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