package com.pe.mascotapp.vistas.alert

import android.app.Activity
import android.app.Fragment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pe.mascotapp.R
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorHorizontal
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.vistas.HorizontalLine
import com.pe.mascotapp.vistas.fragments.home.HomeFragment

@Preview
@Composable
fun AlertScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 29.dp, end = 21.dp, top = 32.dp, bottom = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Alertas",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
                ElevatedButton(
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(colorHeader)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close), // Reemplaza con el ícono de cerrar que tengas
                        contentDescription = "Cerrar",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                (context as? Activity)?.finish()
                        }, tint = colorPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header Section
/*            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Alertas",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = Color(0xFF2C3E50)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close), // Reemplaza con el ícono de cerrar que tengas
                    contentDescription = "Cerrar",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { *//* Acción para cerrar *//* }
                )
            }*/

     //           Spacer(modifier = Modifier.height(8.dp))

            // List of Alerts
            LazyColumn {
                item {
                    AlertItem(
                        imageRes = R.drawable.ic_dog, // Reemplaza con tu imagen
                        message = "Faltan 3 días para tu cita con el veterinario, no te olvides de llevar tus análisis",
                        isRecent = true
                    )
                }
                item {
                    Text(
                        text = "Anteriores:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
                items(5) {
                    AlertItem(
                        imageRes = R.drawable.ic_dog, // Reemplaza con tu imagen
                        message = "Falta 3 días para tu cita con el especialista en profilaxis. Recuerda llevar tus documentos",
                        isRecent = false
                    )
                    HorizontalLine()
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AlertItem(imageRes: Int, message: String, isRecent: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isRecent) colorHorizontal else Color(0xFFFFFFFF),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 18.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = "",
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier
            .weight(1f)
            .padding(end = 12.dp)) {
            Text(
                text = message,
                style = buttonTitleStyle.copy(fontSize = 15.sp, color = colorDisabled),
                color = colorDisabled
            )
        }
        Row(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Top)
                .padding(top = 4.dp), // Adjust the size as needed
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(4.dp) // Adjust the size of each circle
                        .clip(CircleShape)
                        .background(colorPrimary) // Set your desired color
                )}
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAlertScreen() {
    AlertScreen()
}