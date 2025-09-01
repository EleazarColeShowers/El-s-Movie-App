package com.example.elsmovieapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elsmovieapp.R

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit
) {
    val cyan = colorResource(id = R.color.cyan)

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = cyan,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
    }
}
