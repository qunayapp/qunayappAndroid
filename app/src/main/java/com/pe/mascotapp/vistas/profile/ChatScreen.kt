package com.pe.mascotapp.vistas.profile

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pe.mascotapp.R
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.colorYellow
import com.pe.mascotapp.titleStyle

@Composable
fun ChatScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val messages = listOf(
        Message("Tenemos una propuesta perfecta para ti. La veterinaria Big Pets está regalando un juguete en cada consulta todo el mes de Octubre si los etiquetas en IG con el hashtag #BIGPETSVET",
            "9:40am",
            "16/18/23"
        ),
        Message(
            "Tenemos una propuesta perfecta para ti. La veterinaria Big Pets está regalando un juguete en cada consulta todo el mes de Octubre si los etiquetas en IG con el hashtag #BIGPETSVET",
            "9:40am",
            "16/18/23",
            true
        ),
        // Add more messages here
    )
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 21.dp, top = 32.dp, bottom = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(colorHeader)
                ) {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = "Back",
                            tint = colorPrimary
                        )
                    }
                }
                Text(
                    text = "Qunay",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
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

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                for (i in 0 until 2) {
                    messages.forEach { message ->
                        CustomChatBubbleWithDetails(
                            message = message.message,
                            time = message.time,
                            date = message.date,
                            isImage = message.isSender
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }


            }
        }
    }
}


@Composable
fun CustomChatBubbleWithDetails(
    message: String,
    time: String,
    date: String,
    isImage: Boolean = false
) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .zIndex(1f)
                    .background(
                        color = colorHeader,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 16.dp,
                            bottomStart = 8.dp
                        )
                    )
            ) {
                if (isImage) {
                    Box(modifier = Modifier.padding(2.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.img_pet_house), // Cambia a tu imagen
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Text(
                            text = time,
                            style = titleStyle.copy(
                                color = Color.White,
                                fontSize = 10.sp,
                            ),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 8.dp, end = 8.dp)
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .widthIn(max = 250.dp)
                            .padding(12.dp),
                        text = message,
                        style = titleStyle.copy(
                            color = Color(0x993C3C43),
                            fontSize = 16.sp
                        ),
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = time,
                        style = titleStyle.copy(
                            color = colorYellow,
                            fontSize = 10.sp,
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(bottom = 8.dp, end = 8.dp)
                    )
                }
            }
            CustomCorner(
                modifier = Modifier
                    .offset((-5).dp, 4.dp)
                    .align(Alignment.BottomStart)
            )
        }
        Text(
            text = "Enviado  $date",
            style  = titleStyle.copy(
                color = Color(0x993C3C43),
                fontSize = 12.sp,
            ),
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)
        )
    }
}

@Composable
fun CustomCorner(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .background(Color.White)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_coner_chat),
            contentDescription = "Back",
            tint = colorHeader
        )
    }
}
data class Message(val message: String, val time: String, val date: String, val isSender: Boolean = false)