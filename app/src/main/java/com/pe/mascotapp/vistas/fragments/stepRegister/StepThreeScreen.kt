package com.pe.mascotapp.vistas.fragments.stepRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.titleStyle
import com.pe.mascotapp.vistas.entities.PetEntity

val listPets = listOf(
    PetEntity(
        0L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "Asdfasdf asdfasdf",
        "asdfasdf",
        20.0,
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF48A7D3
    ),
    PetEntity(
        1L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "Asdfasdfff ffff",
        "asdfasdf",
        20.0,
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF2A6BAF
    ),
    PetEntity(
        2L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "fffddf fdfds",
        "asdfasdf",
        20.0,
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF48A7D3
    ),
    PetEntity(
        3L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "Asdfasdf",
        "asdfasdf",
        20.0,
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF6EA6E1
    ),
    PetEntity(
        4L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "Asdfasdf",
        "asdfasdf",
        20.0,
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF2A6BAF
    )
)

@Composable
@Preview
fun StepThreeScreen() {
    val currentStep = remember { mutableIntStateOf(2) }

    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_patitas),
            contentDescription = "background_patitas",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3F)
                ) {

                    Column(modifier = Modifier.padding(top = 37.dp)) {
                        Text(
                            text = "Paso 3",
                            textAlign = TextAlign.Center,
                            style = boldTitleStyle.copy(colorPrimary),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Confirma tus datos",
                            textAlign = TextAlign.Center,
                            style = titleStyle.copy(colorMediumBlue),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    StepsProgressBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 87.dp, end = 87.dp, top = 10.dp),
                        numberOfSteps = 2,
                        currentStep = currentStep.intValue
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        PrimaryButton(
                            modifier = Modifier
                                .width(158.dp)
                                .height(158.dp),
                            content = {

                            },
                            shape = RoundedCornerShape(100),
                        ) {

                        }
                        Row {

                            Text(
                                text = "Julian Alvarez",
                                style = titleStyle.copy(color = colorMediumBlue)
                            )

                        }


                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tus Mascotas",
                            style = caprasimoTitleStyle.copy(color = colorPrimary)
                        )
                        //ViewPagerPets(listPets)
                    }
                }
                Column {
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        onClick = { /*TODO*/ },
                        content = {
                            Text(text = "Aceptar", style = buttonTitleStyle.copy(fontSize = 20.sp))
                        })

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        onClick = {  }
                    ) {
                        Text(text = "volver", style = buttonTitleStyle, color = colorPrimary)
                    }
                }
            }
        }
    }
}