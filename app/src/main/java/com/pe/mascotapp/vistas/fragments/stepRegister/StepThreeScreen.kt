package com.pe.mascotapp.vistas.fragments.stepRegister

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.mediumTitleStyle
import com.pe.mascotapp.modelos.Usuario
import com.pe.mascotapp.semiBoldTitleStyle
import com.pe.mascotapp.titleStyle
import com.pe.mascotapp.vistas.AnimationLoading
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import com.pe.mascotapp.vistas.CarosuelTutorialActivity
import com.pe.mascotapp.vistas.StartActivity
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.PetWithBreedsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val listPets = listOf(
    PetEntity(
        0L,
        "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
        "Asdfasdf asdfasdf",
        "asdfasdf",
        "20.0",
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
        "20.0",
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
        "20.0",
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
            "20.0",
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
        "20.0",
        Sex.MALE,
        "01/01/2023",
        false,
        0xFF2A6BAF
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun StepThreeScreen(listPetsBreed: MutableList<PetWithBreedsEntity> = mutableStateListOf()) {
    val selectedImage = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        selectedImage.value = it
    }
    val currentStep = remember { mutableIntStateOf(2) }
    val ctx = LocalContext.current
    val listPets = remember {
        listPetsBreed
    }
    val pagerState = rememberPagerState(pageCount = {
        listPets.size
    })
    val  usuario = (ctx as? CarosuelRegisterActivity)?.usuario ?: Usuario()
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
            containerColor = Color.Transparent,
            bottomBar = {
                Column {
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        onClick = {
                            val intent = Intent(ctx, CarosuelTutorialActivity::class.java)
                            ctx.startActivity(intent)
                        },
                        content = {
                            Text(text = "Aceptar", style = buttonTitleStyle.copy(fontSize = 20.sp))
                        }
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        onClick = {
                            (ctx as? CarosuelRegisterActivity)?.onBackPressed()
                        }
                    ) {
                        Text(text = "Volver", style = buttonTitleStyle, color = colorPrimary)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 31.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    PrimaryButton(
                        modifier = Modifier
                            .width(158.dp)
                            .height(158.dp),
                        content = {
                            AsyncImage(
                                model = selectedImage.value ?: R.drawable.ic_camara,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                contentDescription = ""
                            )
                        },
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(100),
                    ) {
                        launcher.launch("image/*")
                    }

                    BasicEditTextField(
                        300.dp,
                        modifier = Modifier
                            .padding(top = 10.dp, end = 32.dp, start = 32.dp)
                            .fillMaxWidth(),
                        value = usuario.name
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tus Mascotas",
                        style = caprasimoTitleStyle.copy(color = colorPrimary, fontSize = 20.sp)
                    )
                    SimpleViewPagerPets(listPets, pagerState = pagerState)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ElevatedButton(
                            contentPadding = PaddingValues(),
                            onClick = {
                                listPets.add(
                                    0,
                                    PetWithBreedsEntity(
                                        PetEntity(color = getColorIndex(pagerState.pageCount)),
                                        listOf()
                                    )
                                )
                                (ctx as? CarosuelRegisterActivity)?.listPets = listPets
                                (ctx as? CarosuelRegisterActivity)?.addPet()
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFF9F9F9)
                            ),
                            shape = ButtonDefaults.elevatedShape,
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                                .shadow(elevation = 20.dp, shape = CircleShape)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "+",
                                    style = boldTitleStyle.copy(fontSize = 28.sp),
                                    color = colorPrimary
                                )
                                Text(
                                    text = "Agregar\n" + "mascota",
                                    style = boldTitleStyle.copy(fontSize = 9.sp),
                                    color = colorPrimary,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}