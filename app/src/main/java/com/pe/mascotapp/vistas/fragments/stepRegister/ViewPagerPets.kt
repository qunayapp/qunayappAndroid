package com.pe.mascotapp.vistas.fragments.stepRegister

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.semiBoldTitleStyle
import com.pe.mascotapp.textColor
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.PetWithBreedsEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerPets(listPets: MutableList<PetWithBreedsEntity>, pagerState: PagerState) {
    val ctx = LocalContext.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val itemWidth = screenWidth / 3

    val scope = rememberCoroutineScope()
    var actualItem = 0
    var showDialog by remember { mutableStateOf(false) }

    CustomDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false }
    ) {
        Column {
            Box(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surface,
                    )
                    .padding(22.dp)
            ) {
                Text(
                    "¿Estás seguro de que quieres eliminar este perfil?",
                    style = semiBoldTitleStyle,
                    color = textColor
                )
            }
            Row(
                Modifier
                    .padding(top = 73.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Volver",
                        color = textColor,
                        style = buttonTitleStyle.copy(fontSize = 20.sp)
                    )
                }
                Button(onClick = {
                    scope.launch {
                        showDialog = false
                        if (actualItem >= 1) {
                            pagerState.scrollToPage(pagerState.currentPage - 1)
                        }
                        listPets.removeAt(actualItem)
                    }
                }, colors = ButtonDefaults.buttonColors(colorMediumBlue)) {
                    Text(text = "Eliminar", style = buttonTitleStyle.copy(fontSize = 20.sp))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 38.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (listPets.size > 1) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .zIndex(100F),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == pagerState.pageCount - 1) {
                                    pagerState.animateScrollToPage(0)
                                    return@launch
                                }
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .width(66.dp)
                            .height(99.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .padding(end = 11.13.dp)
                                .fillMaxSize()
                                .rotate(180F),
                            contentDescription = "Button Image"
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .zIndex(100F),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == 0) {
                                    pagerState.animateScrollToPage(pagerState.pageCount - 1)
                                    return@launch
                                }
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .width(66.dp)
                            .height(99.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .padding(start = 11.13.dp)
                                .fillMaxSize(),
                            contentDescription = "Button Image"
                        )
                    }
                }
            }
            HorizontalPager(
                pageSpacing = -(itemWidth * 2) - (itemWidth / 2) + 4.dp,
                contentPadding = PaddingValues(
                    start = (itemWidth - 100.dp) / 2,
                    end = (itemWidth - 100.dp) / 2,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(0F),
                state = pagerState,
                beyondViewportPageCount = 3,
                reverseLayout = true,
            ) { page ->

                val show =
                    pagerState.currentPage == page || pagerState.currentPage + 1 == page || pagerState.currentPage + 2 == page
                var normalSize = 154.dp
                when (pagerState.currentPage) {
                    page -> normalSize = 154.dp
                    page - 1 -> normalSize = 134.dp
                    page - 2 -> normalSize = 125.dp
                }
                CircularName(
                    pet = listPets[page].pet,
                    pagerState.currentPage,
                    page,
                    listPets.size,
                    show,
                    normalSize
                ) {
                    actualItem = page
                    showDialog = !showDialog
                    //scope.launch {
                    //    if (page >= 1) {
                    //        pagerState.scrollToPage(pagerState.currentPage - 1)
                    //   }

                    //   listPets.removeAt(page)
                    //}
                }
            }
            ElevatedButton(
                contentPadding = PaddingValues(),
                onClick = {
                    scope.launch {
                        if (!listPets[pagerState.currentPage].pet.isValid()) {
                            Toast.makeText(
                                ctx,
                                "Llena el nombre y la especie de tu mascota",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }
                        listPets.add(
                            0,
                            PetWithBreedsEntity(
                                PetEntity(color = getColorIndex(pagerState.pageCount)),
                                mutableListOf()
                            )
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF9F9F9)
                ),
                shape = ButtonDefaults.elevatedShape,
                modifier = Modifier
                    .padding(start = 200.dp)
                    .zIndex(0F)
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
        Row(
            Modifier
                .fillMaxWidth()
                .height(53.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (listPets.size > 1) {
                repeat(listPets.size) { iteration ->
                    val color =
                        if (listPets.size - pagerState.currentPage - 1 == iteration)
                            colorMediumBlue
                        else Color(0xFFCECECE).copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(9.dp)
                    )
                }
            }
        }
    }
}

fun getColorIndex(position: Int): Long {
    val indexPosition = when {
        (position + 1) % 3 == 0 -> 2

        (position + 2) % 3 == 0 -> 1
        else -> 0
    }
    val colors = listOf(0xFF48A7D3, 0xFF2A6BAF, 0xFF203E6C)
    return colors[indexPosition]
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleViewPagerPets(listPets: MutableList<PetWithBreedsEntity>, pagerState: PagerState) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val ctx = LocalContext.current

    val itemWidth = screenWidth / 3
    var actualItem = 0
    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    CustomDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false }
    ) {
        Column {
            Box(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surface,
                    )
                    .padding(22.dp)
            ) {
                Text(
                    "¿Estás seguro de que quieres eliminar este perfil?",
                    style = semiBoldTitleStyle,
                    color = textColor
                )
            }
            Row(
                Modifier
                    .padding(top = 73.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = "Volver",
                        color = textColor,
                        style = buttonTitleStyle.copy(fontSize = 20.sp)
                    )
                }
                Button(onClick = {
                    scope.launch {
                        showDialog = false
                        if (actualItem >= 1) {
                            pagerState.scrollToPage(pagerState.currentPage - 1)
                        }
                        listPets.removeAt(actualItem)
                    }
                }, colors = ButtonDefaults.buttonColors(colorMediumBlue)) {
                    Text(text = "Eliminar", style = buttonTitleStyle.copy(fontSize = 20.sp))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 38.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (listPets.size > 1) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .zIndex(100F),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage >= pagerState.pageCount - 2) {
                                    pagerState.animateScrollToPage(0)
                                    return@launch
                                }
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .width(66.dp)
                            .height(99.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .padding(end = 11.13.dp)
                                .fillMaxSize()
                                .rotate(180F),
                            contentDescription = "Button Image"
                        )
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .zIndex(100F),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == 0) {
                                    pagerState.animateScrollToPage(pagerState.pageCount - 2)
                                    return@launch
                                }
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .width(66.dp)
                            .height(99.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            modifier = Modifier
                                .padding(start = 11.13.dp)
                                .fillMaxSize(),
                            contentDescription = "Button Image"
                        )
                    }
                }
            }
            val paddingValues = if (listPets.size > 1) PaddingValues(
                start = itemWidth * 2 - (itemWidth / 2) + 5.dp,
                end = (itemWidth / 2) - 20.dp,
            ) else PaddingValues(0.dp)
            HorizontalPager(
                pageSpacing = 0.dp,
                userScrollEnabled = false,
                contentPadding = paddingValues,
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                beyondViewportPageCount = 2,
                reverseLayout = true,
            ) { page ->
                val show =
                    pagerState.currentPage == page || pagerState.currentPage + 1 == page
                val normalSize = 137.dp
                CircularName(
                    pet = listPets[page].pet,
                    pagerState.currentPage,
                    page,
                    listPets.size,
                    show,
                    normalSize,
                    canEdit = {
                        (ctx as? CarosuelRegisterActivity)?.listPets = listPets
                        (ctx as? CarosuelRegisterActivity)?.editPet(page)
                    }
                ) {
                    actualItem = page
                    showDialog = !showDialog
                }
            }

        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (listPets.size > 1) {
                repeat(listPets.size) { iteration ->
                    val color =
                        if (listPets.size - pagerState.currentPage - 1 == iteration)
                            colorMediumBlue
                        else Color(0xFFCECECE).copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(9.dp)
                    )
                }
            }
        }

    }
}
