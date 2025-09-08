package com.example.elsmovieapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.elsmovieapp.R
import com.example.elsmovieapp.data.model.CastMember
import com.example.elsmovieapp.data.model.Movie

@Composable
fun MovieDetailPage(
    movie: Movie,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    cast: List<CastMember> = emptyList(),

    ) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Poster
        val posterUrl = movie.poster_path?.let { "https://image.tmdb.org/t/p/w780$it" }
        AsyncImage(
            model = posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Title
        Text(
            text = movie.title ?: "Unknown Title",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Year • Runtime • Genre
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val year = movie.release_date?.take(4) ?: "N/A"
            Text(text = year, color = Color.Gray, fontSize = 14.sp)

            movie.runtime?.let {
                Text(text = "${it} min", color = Color.Gray, fontSize = 14.sp)
            }

            Text(
                text = movie.genre_ids.joinToString { id ->
                    genreMap.entries.firstOrNull { it.value == id }?.key ?: ""
                },
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // About Movie Section
        Text(
            text = "About Movie",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.overview.ifEmpty { "No description available." },
            style = TextStyle(fontSize = 16.sp, color = Color.White, lineHeight = 22.sp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (cast.isNotEmpty()) {
            Text(
                text = "Cast",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                cast.take(5).forEach { castMember ->
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Column {
                            Text(
                                text = castMember.name,
                                style = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            )
                            Text(
                                text = "as ${castMember.character}",
                                style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                            )
                        }
                    }
                }
            }
        }

    }
}
