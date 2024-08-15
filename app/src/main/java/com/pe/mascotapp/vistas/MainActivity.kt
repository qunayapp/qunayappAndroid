package com.pe.mascotapp.vistas

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.R
import com.pe.mascotapp.skyBlue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.background_main_screen),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_qunay),
                contentDescription = null,
                modifier = Modifier.size(220.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, CarosuelRegisterActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(18.dp)
                    .width(240.dp).height(50.dp),
                shape = RoundedCornerShape(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue_primary),
                )
            ) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.next),
                    color = Color.White,
                    fontFamily = FontFamily(
                        Font(
                            R.font.worksans_regular
                        )
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.label_register_with),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.worksans_regular
                        )
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.sigin_google_button),
                contentDescription = null,
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
            )

            Spacer(
                modifier = Modifier.height(
                    20.dp
                )
            )

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(6.dp)
                    .background(
                        color = colorResource(id = R.color.blue_primary),
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            Spacer(
                modifier = Modifier.height(
                    22.dp
                )
            )
            MyTextWithClickablePart(context = context)

        }
    }
}


@Composable
fun MyTextWithClickablePart( context: Context) {
    val annotatedText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("¿Ya estás registrado? ")
        }
        pushStringAnnotation(tag = "clickable", annotation = "link") // Add an annotation to the clickable part
        withStyle(style = SpanStyle(color = skyBlue)) { // Use your skyBlue color here
            append("Ingresa aquí")
        }
        pop() // Remove the annotation
    }

    Text(
        textAlign = TextAlign.Center,
        text = annotatedText,
        fontFamily = FontFamily(Font(R.font.worksans_regular)),
        fontSize = 14.sp,
        modifier = Modifier.clickable {
            // Handle the click event for the entire text
            annotatedText.getStringAnnotations(tag = "clickable", start = 0, end = annotatedText.length)
                .firstOrNull()?.let { annotation ->
                    // Check if the click was on the "Ingresa aquí" part
                    if (annotation.item == "link") {
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent) }
                }
        }
    )
}