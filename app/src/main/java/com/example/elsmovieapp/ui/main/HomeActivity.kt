package com.example.elsmovieapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.elsmovieapp.R
import com.example.elsmovieapp.data.model.Movie
import com.example.elsmovieapp.ui.viewmodel.AuthViewModel
import com.example.elsmovieapp.ui.viewmodel.AuthViewModelFactory
import com.example.elsmovieapp.data.repository.AuthRepository
import com.example.elsmovieapp.data.repository.MovieRepository
import com.example.elsmovieapp.ui.components.MoviePlaceholderCard
import com.example.elsmovieapp.ui.components.SearchBar
import com.example.elsmovieapp.ui.theme.ElsMovieAppTheme
import com.example.elsmovieapp.ui.viewmodel.MovieViewModel
import com.example.elsmovieapp.ui.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    val categories = listOf("All", "Comedy", "Animation", "Series", "Documentary")
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val username by viewModel.username.observeAsState()
    val movieViewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(MovieRepository()))
    val movies by movieViewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchMovies()
    }


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

        Spacer(modifier = Modifier.height(8.dp))
        SearchBar(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
            }
        )
        Spacer(modifier = Modifier.height(6.dp))

        HeroCarouselCards(movies = movies)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Categories",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory

                Text(
                    text = category,
                    modifier = Modifier
                        .clickable { selectedCategory = category }
                        .background(
                            color = if (isSelected) Color.Cyan.copy(alpha = 0.45f) else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Cyan else Color.DarkGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isSelected) Color.Cyan else Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(10) { // 10 placeholder items
                MoviePlaceholderCard()
            }
        }
    }
}

@Composable
fun HeroCarouselCards(
    movies: List<Movie>,
    peekRightDp: Dp = 50.dp,
    pageSpacing: Dp = 16.dp,
    cardHeight: Dp = 340.dp,     // a bit taller as you wanted
    cornerRadius: Dp = 24.dp
) {
    if (movies.isEmpty()) return

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth - peekRightDp
    val sidePad = (screenWidth - cardWidth) / 2

    val totalPages = Int.MAX_VALUE
    val startPage = totalPages / 2 - (totalPages / 2 % movies.size)
    val pagerState = rememberPagerState(
        initialPage = startPage,
        pageCount = { totalPages }
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000L)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pagerState.pageCount)
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight),
        pageSize = PageSize.Fixed(cardWidth),
        pageSpacing = pageSpacing,
        contentPadding = PaddingValues(start = 0.dp, end = peekRightDp)
    ) { page ->
        val movie = movies[page % movies.size]

        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val dist = pageOffset.absoluteValue.coerceIn(0f, 1f)
        val scale = 1f - 0.12f * dist
        val alpha = 1f - 0.25f * dist

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .height(cardHeight)
                    .aspectRatio(2f / 3f)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    },
                shape = RoundedCornerShape(cornerRadius),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(Modifier.fillMaxSize()) {
                    val posterUrl = movie.poster_path?.let { "https://image.tmdb.org/t/p/w780$it" }
                    AsyncImage(
                        model = posterUrl,
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop, // center-crop
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.28f))
                    )

                    Text(
                        text = movie.title,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
