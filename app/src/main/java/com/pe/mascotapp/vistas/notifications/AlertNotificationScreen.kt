package com.pe.mascotapp.vistas.notifications

import androidx.compose.animation.AnimatedVisibility

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.Button
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf


import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.semiBoldTitleStyle


@Preview
@Composable
fun BottomNotificationExample() {

    var showNotification by remember { mutableStateOf(false) }

    val items = listOf(
        Pair(Icons.Filled.Home, "Home"),
        Pair(Icons.Filled.Settings, "Settings"),
        Pair(Icons.Filled.Person, "Profile"),
        Pair(Icons.Filled.Favorite, "Favorites"),
        Pair(Icons.Filled.Search, "Search"),
        Pair(Icons.Filled.Email, "Email")
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = { showNotification = !showNotification },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = if (showNotification) "Hide Notification" else "Show Notification")
        }
        AnimatedVisibility(
            visible = showNotification,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
        ) {
            NotificationCard(
                onDismiss = { showNotification = false }
            )
        }
    }
}

@Composable
fun NotificationCard(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color(0xFF323232), RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "This is a notification!",
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDismiss) {
                Text(text = "Dismiss")
            }
        }
    }
}

@Composable
fun AdaptiveBoxWithText(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val borderColor = if (isClicked) Color.Red else Color.Gray
    val iconColor = if (isClicked) Color.Blue else Color.Black

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Maintain aspect ratio
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                .clickable { isClicked = !isClicked }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,contentDescription = "Box Icon",
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = text,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

