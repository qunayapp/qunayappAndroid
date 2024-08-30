package com.pe.mascotapp.vistas.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pe.mascotapp.R
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.buttonTitleStyle


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    "Qunay",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Acción de regreso */ }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFFD8E8F2)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            MessageBubble(
                text = "Tenemos una propuesta perfecta para ti. La veterinaria Big Pets está regalando un juguete en cada consulta todo el mes de Octubre si los etiquetas en IG con el hashtag #BIGPETSVET",
                time = "16/18/23",
                isImage = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            MessageBubble(
                imageUrl = "https://example.com/image.png", // Reemplaza con tu imagen
                time = "16/18/23",
                isImage = true
            )
        }
    }
}

@Composable
fun MessageBubble(text: String = "", imageUrl: String = "", time: String, isImage: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        if (isImage) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Cambia a tu imagen
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(Color(0xFFE1F5FE), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Enviado $time",
            style = buttonTitleStyle.copy(fontSize = 12.sp, color = Color(0x993C3C43)),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}