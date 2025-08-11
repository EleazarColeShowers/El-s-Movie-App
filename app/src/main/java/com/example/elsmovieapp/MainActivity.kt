package com.example.elsmovieapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elsmovieapp.model.OnboardingPage
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme
import kotlinx.coroutines.launch
import kotlin.jvm.java


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElsMovieAppTheme {
                val dark = colorResource(id = R.color.dark)

                Scaffold(

                    modifier = Modifier.fillMaxSize(),
                    containerColor = dark
                ) { innerPadding ->
                    Onboarding(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Onboarding(modifier: Modifier){
    val black= colorResource(id=R.color.black)
    val cyan= colorResource(id= R.color.cyan)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        OnboardingPage(R.drawable.firstonboarding, "Offers ad-free viewing of high quality", "Watch your movies without interruptions"),
        OnboardingPage(R.drawable.secondonboarding, "Be Up-To-Date", "Keep track of the latest movies"),
        OnboardingPage(R.drawable.thirdonboarding, "Our service brings together your favorite series", "All of your favorite series and movies, in one place")
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(399.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(pages[page].image),
                    contentDescription = "Onboarding Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(371.dp)
                .padding(bottom = 40.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = black),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = pages[pagerState.currentPage].title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = pages[pagerState.currentPage].description,
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = "Next",
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                coroutineScope.launch {
                                    val nextPage = pagerState.currentPage + 1
                                    if (nextPage < pages.size) {
                                        pagerState.animateScrollToPage(nextPage)
                                    } else {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                Splash::class.java
                                            )
                                        )
                                    }
                                }
                            },
                        tint = Color.Unspecified
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(pages.size) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(if (isSelected) 32.dp else 10.dp)
                                    .padding(2.dp)
                                    .background(
                                        color = if (isSelected) cyan else cyan.copy(alpha = 0.4f),
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
