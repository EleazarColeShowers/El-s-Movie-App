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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
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
import com.example.elsmovieapp.ui.components.BottomBar
import com.example.elsmovieapp.ui.components.MovieCard
import com.example.elsmovieapp.ui.components.MovieDetailPage
import com.example.elsmovieapp.ui.components.SearchBar
import com.example.elsmovieapp.ui.components.categories
import com.example.elsmovieapp.ui.components.genreMap
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
    val dark = colorResource(id = R.color.dark)

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val username by viewModel.username.observeAsState()
    val movieViewModel: MovieViewModel =
        viewModel(factory = MovieViewModelFactory(MovieRepository()))

    val movies by movieViewModel.movies.collectAsState()
    val nowPlaying by movieViewModel.nowPlaying.collectAsState()
    val topRated by movieViewModel.topRated.collectAsState()
    val upcoming by movieViewModel.upcoming.collectAsState()

    var selectedMovie by remember { mutableStateOf<Movie?>(null) }


    LaunchedEffect(Unit) {
        movieViewModel.fetchMovies()
        movieViewModel.fetchNowPlaying()
        movieViewModel.fetchTopRated()
        movieViewModel.fetchUpcoming()
        viewModel.fetchUserName()
    }

    val filteredMovies = if (selectedCategory == "All") {
        movies
    } else {
        val genreId = genreMap[selectedCategory]   // ✅ using imported genreMap
        movies.filter { it.genre_ids.contains(genreId) }
    }

    Scaffold(
        bottomBar = { BottomBar(currentScreen = "Home") },
        containerColor = dark
    ) { innerPadding ->
        if (selectedMovie == null) {

            Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
                onValueChange = { newValue -> searchQuery = newValue }
            )
            Spacer(modifier = Modifier.height(6.dp))

            HeroCarouselCards(movies = movies, onMovieClick = { selectedMovie = it })

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

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->   // ✅ using imported categories
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

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredMovies) { movie ->
                    MovieCard(movie = movie, genreMap = genreMap, onMovieClick = { selectedMovie = it })
                }

                item {
                    Box(
                        modifier = Modifier
                            .width(135.dp)
                            .height(231.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray)
                            .clickable { movieViewModel.fetchMovies() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Load More",
                            color = Color.Cyan,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            MovieSection(
                title = "Now Playing",
                movies = nowPlaying,
                onLoadMore = { movieViewModel.fetchNowPlaying() },
                genreMap = genreMap,
                onMovieClick = { selectedMovie = it }
            )

            MovieSection(
                title = "Top Rated",
                movies = topRated,
                onLoadMore = { movieViewModel.fetchTopRated() },
                genreMap = genreMap,
                onMovieClick = { selectedMovie = it }
            )

            MovieSection(
                title = "Upcoming",
                movies = upcoming,
                onLoadMore = { movieViewModel.fetchUpcoming() },
                genreMap = genreMap,
                onMovieClick = { selectedMovie = it }
            )
        }
        }
        else {
            LaunchedEffect(selectedMovie!!.id) {
                movieViewModel.fetchCastDetails(selectedMovie!!.id)
            }
            MovieDetailPage(
                movie = selectedMovie!!,
                onBack = { selectedMovie = null },
                cast = movieViewModel.movieCast.collectAsState().value

            )
        }
    }
}

@Composable
fun MovieSection(
    title: String,
    movies: List<Movie>,
    onLoadMore: () -> Unit,
    genreMap: Map<String, Int>,
    onMovieClick: (Movie) -> Unit = {}
) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = title,
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    )
    Spacer(modifier = Modifier.height(15.dp))

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(movies) { movie ->
            MovieCard(movie = movie, genreMap = genreMap, onMovieClick = { onMovieClick(movie) })
        }

        item {
            LoadMoreCard { onLoadMore() }
        }
    }
}

@Composable
fun LoadMoreCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(135.dp)
            .height(231.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Load More",
            color = Color.Cyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeroCarouselCards(
    movies: List<Movie>,
    peekRightDp: Dp = 50.dp,
    pageSpacing: Dp = 16.dp,
    cardHeight: Dp = 340.dp,
    cornerRadius: Dp = 24.dp,
    onMovieClick: (Movie) -> Unit = {}
) {
    if (movies.isEmpty()) return

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth - peekRightDp

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
                    .clickable { onMovieClick(movie) }
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

                }
            }
        }
    }
}
