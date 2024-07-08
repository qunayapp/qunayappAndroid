package com.pe.mascotapp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val workSansFontFamily = FontFamily(
    Font(R.font.worksans_bold, FontWeight.Bold),
    Font(R.font.worksans_regular, FontWeight.Normal),
    Font(R.font.worksans_light, FontWeight.Light)
)

val bigTitleStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 70.sp,
    fontWeight = FontWeight.Bold
)

val titleStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal
)

val buttonTitleStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 15.sp,
    fontWeight = FontWeight.Normal
)

val boldTitleStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
)

val descriptionTextStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 30.sp,
    fontWeight = FontWeight.Light
)

val textFieldTextStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 14.sp,
    fontWeight = FontWeight.Light
)

val chipTextStyle = TextStyle(
    fontFamily = workSansFontFamily,
    fontSize = 11.sp,
    fontWeight = FontWeight.Normal
)



val colorDisabled = Color(0xFFC7C7C7)
val colorPrimary = Color(0xFF203E6C)
val colorMediumBlue = Color(0xFF2A6BAF)
val skyBlue = Color(0xFF48A7D3)
val colorLightGray = Color(0xFFE9E9E9)
val colorYellow = Color(0xFFF2C953)
val colorCyan =Color(0xFFE4EFF3)