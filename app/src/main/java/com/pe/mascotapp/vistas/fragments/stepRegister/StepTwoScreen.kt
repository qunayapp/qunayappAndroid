package com.pe.mascotapp.vistas.fragments.stepRegister

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pe.mascotapp.R
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.bigTitleStyle
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.chipTextStyle
import com.pe.mascotapp.colorCyan
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.colorLightGray
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.colorYellow
import com.pe.mascotapp.textFieldTextStyle
import com.pe.mascotapp.titleStyle
import com.pe.mascotapp.vistas.entities.PetEntity
import kotlinx.coroutines.launch

@Preview
@Composable
fun StepTwoScreen() {
    val currentStep = remember { mutableIntStateOf(1) }

    var namePet by remember { mutableStateOf("") }

    var weightPet by remember { mutableStateOf("") }

    var calendarPet by remember { mutableStateOf("") }

    var kindPet by remember {
        mutableStateOf(KindPet.None)
    }

    var sexPet by remember {
        mutableStateOf(SexPet.None)
    }

    val listPets = listOf(
        PetEntity(
            0L,
            "https://i.pinimg.com/236x/a6/b8/3c/a6b83c77cd06e23e2d956ce241776e24.jpg",
            "Asdfasdf asdfasdf",
            "asdfasdf",
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
            "asdfasdf",
            20.0,
            Sex.MALE,
            "01/01/2023",
            false,
            0xFF2A6BAF
        )
    )

    val weightRegex = Regex("^[0-9]+(\\.[0-9]{0,2})?\$")
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
                Column(modifier = Modifier.padding(top = 37.dp)) {
                    Text(
                        text = "Paso 2",
                        textAlign = TextAlign.Center,
                        style = boldTitleStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Registra a tu mascota",
                        textAlign = TextAlign.Center,
                        style = titleStyle,
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

                ViewPagerPets(listPets)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(18.11.dp)
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.mascotas),
                        value = namePet,
                        onValueChange = {
                            namePet = it
                        },
                        label = "¿Cómo se llama tu mascota? "
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconTextButton(
                            "Perro",
                            R.drawable.perro,
                            kindPet == KindPet.Dog,
                            Modifier
                                .weight(1F)
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max),
                            onClick = {
                                kindPet = KindPet.Dog
                            }
                        )
                        IconTextButton(
                            "Gato",
                            R.drawable.gato,
                            kindPet == KindPet.Cat,
                            Modifier
                                .weight(1F)
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max),
                            onClick = {
                                kindPet = KindPet.Cat
                            }
                        )
                        IconTextButton(
                            "Otro",
                            R.drawable.llama,
                            kindPet == KindPet.Other,
                            Modifier
                                .weight(1F)
                                .fillMaxHeight()
                                .width(IntrinsicSize.Max),
                            onClick = {
                                kindPet = KindPet.Other
                            }
                        )
                    }

                    ChipGroup()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(53.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        IconTextButton(
                            name = "Macho",
                            icon = R.drawable.hombre,
                            isEnabled = sexPet == SexPet.Male,
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            onClick = {
                                sexPet = SexPet.Male
                            }
                        )
                        IconTextButton(
                            name = "Hembra",
                            icon = R.drawable.mujer,
                            isEnabled = sexPet == SexPet.Female,
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            onClick = {
                                sexPet = SexPet.Female
                            }
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        CustomTextField(
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            leadingIcon = painterResource(id = R.drawable.peso),
                            value = weightPet,
                            onValueChange = {
                                if (weightRegex.matches(it)) {
                                    weightPet = it
                                }
                            },
                            suffix = "kg",
                            label = "Peso",
                            textAlign = TextAlign.End,
                            keyBoarType = KeyboardType.Decimal
                        )
                        CustomTextField(
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            leadingIcon = painterResource(id = R.drawable.edad),
                            value = calendarPet,
                            onValueChange = {
                                calendarPet = it
                            },
                            label = "Edad",
                            keyBoarType = KeyboardType.Text,
                            visualTransformation = DateTransformation()
                        )
                    }
                }
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 44.dp)
                        .height(58.dp)
                        .padding(horizontal = 77.dp),
                    onClick = { /*TODO*/ },
                    content = {
                        Text(text = "siguiente", style = buttonTitleStyle.copy(fontSize = 20.sp))
                    })

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .padding(horizontal = 77.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    onClick = { /*TODO*/ }) {
                    Text(text = "volver", style = buttonTitleStyle, color = colorPrimary)
                }
            }
        }

    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier,
    content: @Composable () -> Unit,
    shape: Shape = RoundedCornerShape(60.dp),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick.invoke() },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            colorMediumBlue
        )
    ) {
        content.invoke()
    }
}

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0..numberOfSteps) {
            Step(
                modifier = if (step == 0) Modifier.width(15.dp) else Modifier
                    .weight(1F)
                    .padding(start = 0.dp),
                isCompete = step <= currentStep,
            )
        }
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean) {
    val color = if (isCompete) colorPrimary else colorLightGray

    Box(modifier = modifier) {
        //Line
        Divider(
            modifier = Modifier.align(Alignment.CenterStart),
            color = color,
            thickness = 2.dp
        )

        //Circle
        Canvas(modifier = Modifier
            .size(15.dp)
            .align(Alignment.CenterEnd)
            .border(
                shape = CircleShape,
                width = 2.dp,
                color = colorLightGray
            ),
            onDraw = {
                drawCircle(color = color)
            }
        )
    }
}

@Composable
fun CircularName(pet: PetEntity, currentPage: Int, page: Int, totalItems: Int = 0, show: Boolean, size: Dp = 154.dp) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .width(IntrinsicSize.Max)
            .zIndex(if (currentPage == page) totalItems.toFloat() else (totalItems - page).toFloat())
    ) {
        AnimatedVisibility(visible = show,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                fullWidth / 3
            } + fadeIn(
                animationSpec = tween(durationMillis = 200)
            ),
            exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                200
            } + fadeOut()) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(Color(pet.color)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getInitials(pet.name),
                    style = bigTitleStyle,
                    color = colorCyan
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { /* Handle button click */ },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Red
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(40.dp)
                        .height(40.dp)
                        .background(colorYellow)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_trash),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(7.dp),
                        contentDescription = "Button Image"
                    )
                }
            }

        }


    }
}

fun getInitials(name: String): String {
    return name.split(" ")
        .map { it.first().uppercaseChar() }
        .joinToString("")
}


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Painter? = null,
    suffix: String? = null,
    label: String = "",
    textAlign: TextAlign = TextAlign.Start,
    keyBoarType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        leadingIcon = {
            if (leadingIcon != null)
                Icon(
                    painter = leadingIcon, contentDescription = null
                )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = colorDisabled,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = colorPrimary,
            focusedBorderColor = colorPrimary,
            unfocusedBorderColor = colorDisabled,
        ),
        label = { Text(text = label, style = textFieldTextStyle) },
        suffix = { Text(text = suffix ?: "") },
        textStyle = LocalTextStyle.current.copy(textAlign = textAlign),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoarType
        ),
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerPets(listPets: List<PetEntity>) {
    val pagerState = rememberPagerState(pageCount = {
        listPets.size
    })
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemWidth = screenWidth / 3

    val scope = rememberCoroutineScope()

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
            Row(
                Modifier
                    .fillMaxWidth()
                    .zIndex(100F),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
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
                beyondBoundsPageCount = 3,
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
                    pet = listPets[page],
                    pagerState.currentPage,
                    page,
                    listPets.size,
                    show,
                    normalSize
                )
            }
            ElevatedButton(
                contentPadding = PaddingValues(),
                onClick = {

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

@Composable
fun IconWithText(name: String, icon: Int, isEnabled: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "Email Icon",
            colorFilter = if (isEnabled) ColorFilter.tint(colorPrimary) else ColorFilter.tint(
                colorDisabled
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = buttonTitleStyle,
            color = if (isEnabled) colorPrimary else colorDisabled
        )
    }
}

@Composable
fun IconTextButton(
    name: String,
    icon: Int,
    isEnabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = { onClick.invoke() }, modifier = modifier,
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(1.81.dp, if (isEnabled) colorPrimary else colorDisabled)
    ) {
        IconWithText(name, icon, isEnabled)
    }
}

@Composable
fun CustomChip(name: String, delete: (name: String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(20.dp)
            .border(
                border = BorderStroke(1.dp, Color(0xFF000000)),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(4.dp)
            .clickable {
                delete.invoke(name)
            }
    ) {
        Image(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "",
        )
        Text(text = name, maxLines = 1, overflow = TextOverflow.Ellipsis, style = chipTextStyle)
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun ChipGroup() {
    var words by remember {
        mutableStateOf(
            listOf(
                "Alaskan Klee Kay",
                "Alaskan husky",
                "Alaskan malmute",
                "Alaskan clasic",
                "Alaskan husky",
                "Alaskan husky",
                "Alaskan husky",
                "Alaskan husky",
                "Alaskan husky"
            )
        )
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth(1F)
            .border(
                border = BorderStroke(width = 1.81.dp, colorPrimary),
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.estrella),
            contentDescription = "",
            modifier = Modifier.height(20.dp)
        )
        words.forEach { word ->
            CustomChip(word) {
                words = words.toMutableList().apply { remove(it) }
            }
        }
    }
}

enum class KindPet {
    Dog,
    Cat,
    Other,
    None
}

enum class SexPet {
    Male,
    Female,
    None
}

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}
