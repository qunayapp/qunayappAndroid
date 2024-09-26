package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.pe.mascotapp.R
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
import com.pe.mascotapp.domain.models.Sex
import com.pe.mascotapp.mediumTitleStyle
import com.pe.mascotapp.skyBlue
import com.pe.mascotapp.textFieldTextStyle
import com.pe.mascotapp.titleStyle
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import com.pe.mascotapp.vistas.entities.PetEntity
import com.pe.mascotapp.vistas.entities.PetWithBreedsEntity
import com.pe.mascotapp.vistas.fragments.stepRegister.SelectBreedActivity.Companion.BUNDLE_BREED
import java.text.DecimalFormat
import java.util.Calendar
import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun StepTwoScreen(listPetsBreed: MutableList<PetWithBreedsEntity> = mutableStateListOf()) {
    val currentStep = remember { mutableIntStateOf(1) }
    val ctx = LocalContext.current
    val listPets = remember {
        listPetsBreed
    }

    val pagerState = rememberPagerState(pageCount = {
        listPets.size
    })

    LaunchedEffect(1) {
        pagerState.scrollToPage((ctx as? CarosuelRegisterActivity)?.indexEdit ?: 0)
    }

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
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        onClick = {
                            listPets.mapNotNull { if (it.pet.isValid()) it else null }.apply {
                                if (this.isEmpty() || this.size != listPets.size) {
                                    Toast.makeText(
                                        ctx,
                                        "Asegúrate de que tus mascotas tengan nombre y especie.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@PrimaryButton
                                }
                                (ctx as? CarosuelRegisterActivity)?.listPets = this
                                (ctx as? CarosuelRegisterActivity)?.nextStep()
                            }
                        },
                        content = {
                            Text(
                                text = stringResource(R.string.next),
                                style = buttonTitleStyle.copy(fontSize = 20.sp)
                            )
                        })

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .padding(horizontal = 77.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color.Transparent
                        ),
                        onClick = {
                            (ctx as? CarosuelRegisterActivity)?.listPets = listPets
                            (ctx as? CarosuelRegisterActivity)?.onBackPressed()
                        }) {
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
                    .padding(bottom = 20.dp)
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

                ViewPagerPets(listPets, pagerState)

                FormPet(listPets, pagerState)
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FormPet(listPets: MutableList<PetWithBreedsEntity>, pagerState: PagerState) {
    val ctx = LocalContext.current

    fun setCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            ctx,
            androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val pet = listPets[pagerState.currentPage].pet
                listPets[pagerState.currentPage] =
                    listPets[pagerState.currentPage].copy(
                        pet = pet.copy(
                            birthdate = "${
                                dayOfMonth.toString().padStart(2, '0')
                            }${(month + 1).toString().padStart(2, '0')}${
                                year.toString().padStart(4, '0')
                            }"
                        )
                    )
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(18.11.dp)
    ) {
        CustomTextField(
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = painterResource(id = R.drawable.mascotas),
            value = listPets[pagerState.currentPage].pet.name,
            onValueChange = {
                val pet = listPets[pagerState.currentPage].pet
                listPets[pagerState.currentPage] = listPets[pagerState.currentPage].copy(
                    pet = pet.copy(name = it)
                )
            },
            label = "¿Cómo se llama tu mascota? "
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconTextButton(
                "Perro",
                R.drawable.perro,
                listPets[pagerState.currentPage].pet.specie == KindPet.Dog.value(),
                Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max),
                onClick = {
                    val pet = listPets[pagerState.currentPage].pet
                    var breed = listPets[pagerState.currentPage].breeds
                    if (listPets[pagerState.currentPage].pet.specie != KindPet.Dog.value()) {
                        breed = emptyList()
                    }
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(
                            pet = pet.copy(specie = KindPet.Dog.value()),
                            breeds = breed
                        )
                }
            )
            IconTextButton(
                "Gato",
                R.drawable.gato,
                listPets[pagerState.currentPage].pet.specie == KindPet.Cat.value(),
                Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max),
                onClick = {
                    var breed = listPets[pagerState.currentPage].breeds
                    if (listPets[pagerState.currentPage].pet.specie != KindPet.Cat.value()) {
                        breed = emptyList()
                    }
                    val pet = listPets[pagerState.currentPage].pet
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(
                            pet = pet.copy(specie = KindPet.Cat.value()),
                            breeds = breed
                        )
                }
            )
            IconTextButton(
                "Otro",
                R.drawable.llama,
                listPets[pagerState.currentPage].pet.specie == KindPet.Other.value(),
                Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max),
                onClick = {
                    val pet = listPets[pagerState.currentPage].pet
                    var breed = listPets[pagerState.currentPage].breeds
                    if (listPets[pagerState.currentPage].pet.specie != KindPet.Other.value()) {
                        breed = emptyList()
                    }
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(
                            pet = pet.copy(specie = KindPet.Other.value()),
                            breeds = breed
                        )
                }
            )
        }

        ChipGroup(listPets, pagerState)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            IconTextButton(
                name = "Macho",
                icon = R.drawable.hombre,
                isEnabled = listPets[pagerState.currentPage].pet.sex == Sex.MALE,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                onClick = {
                    val pet = listPets[pagerState.currentPage].pet
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(pet = pet.copy(sex = Sex.MALE))
                }
            )
            IconTextButton(
                name = "Hembra",
                icon = R.drawable.mujer,
                isEnabled = listPets[pagerState.currentPage].pet.sex == Sex.FEMALE,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight(),
                onClick = {
                    val pet = listPets[pagerState.currentPage].pet
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(pet = pet.copy(sex = Sex.FEMALE))
                }
            )
        }
        val weightRegex = Regex("^[0-9]+(\\.[0-9]{0,2})?\$")

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
                value = listPets[pagerState.currentPage].pet.weight,
                onValueChange = {
                    if (it.isNotEmpty() && !weightRegex.matches(it)) return@CustomTextField
                    if (it.length > 9) return@CustomTextField
                    val amount = if (it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    val pet = listPets[pagerState.currentPage].pet
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(pet = pet.copy(weight = amount))
                },
                label = "Peso",
                textAlign = TextAlign.End,
                keyBoarType = KeyboardType.Decimal,
                suffix = "kg"
            )
            CustomTextField(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
                    .clickable { setCalendar() },
                enabled = false,
                leadingIcon = painterResource(id = R.drawable.edad),
                value = listPets[pagerState.currentPage].pet.birthdate.replace("/", ""),
                onValueChange = {
                    if (!it.contains(".") && !it.contains(",") && !it.contains(" ") && it.length < 9) {
                        val pet = listPets[pagerState.currentPage].pet
                        listPets[pagerState.currentPage] =
                            listPets[pagerState.currentPage].copy(pet = pet.copy(birthdate = it))
                    }
                },
                label = "Edad",
                keyBoarType = KeyboardType.Number,
                visualTransformation = DateTransformation(),
                leadingIconOnClick = {
                    setCalendar()
                }
            )
        }
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit,
    shape: Shape = RoundedCornerShape(60.dp),
    onClick: () -> Unit
) {
    Button(
        contentPadding = contentPadding,
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
fun CircularName(
    pet: PetEntity,
    currentPage: Int,
    page: Int,
    totalItems: Int = 0,
    show: Boolean,
    size: Dp = 154.dp,
    canEdit: (() -> Unit)? = null,
    delete: () -> Unit = {},
) {
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
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(Color(pet.color)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = pet.name.getInitials(),
                            style = bigTitleStyle,
                            color = colorCyan
                        )
                    }
                    if (totalItems > 1) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { delete.invoke() },
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
                canEdit?.let {
                    BasicEditTextField(
                        110.dp,
                        Modifier.fillMaxWidth(),
                        mediumTitleStyle.copy(
                            color = colorDisabled,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        ),
                        iconSize = 20.dp,
                        value = pet.name
                    ) {
                        canEdit()
                    }
                }
            }

        }
    }
}

fun String.getInitials(): String {
    val initials = this.trim().split(" ")
        .map { it.firstOrNull()?.uppercaseChar() ?: "" }
        .joinToString("")
    if (initials.length >= 2) {
        return initials.substring(0, 2)
    }
    return initials
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
    leadingIconOnClick: () -> Unit = {},
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        enabled = enabled,
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
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        leadingIconOnClick()
                    }
                )
        },

        trailingIcon = trailingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            cursorColor = colorPrimary,
            focusedBorderColor = colorPrimary,
            unfocusedBorderColor = colorDisabled,
            focusedLabelColor = colorPrimary,
        ),
        label = { Text(text = label, style = textFieldTextStyle) },
        suffix = { Text(text = suffix ?: "") },
        textStyle = LocalTextStyle.current.copy(textAlign = textAlign),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyBoarType
        ),
        visualTransformation = visualTransformation,
    )
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
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.81.dp, if (isEnabled) colorPrimary else colorDisabled),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
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
            .background(skyBlue, shape = RoundedCornerShape(6.dp))
            .padding(4.dp)
            .clickable {
                delete.invoke(name)
            }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "",
            tint = Color.White,
        )
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = chipTextStyle.copy(Color.White)
        )
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChipGroup(listPets: MutableList<PetWithBreedsEntity>, pagerState: PagerState) {
    val context = LocalContext.current

    val breedLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            listPets[pagerState.currentPage] = listPets[pagerState.currentPage].copy(
                breeds = (
                        result.data
                            ?.getParcelableArrayExtra(BUNDLE_BREED)
                            ?.filterIsInstance<BreedPetEntity>()
                            ?: emptyList()
                        )
            )
        }
    }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth(1F)
            .border(
                border = BorderStroke(width = 1.81.dp, colorDisabled),
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .clickable {
                if (listPets[pagerState.currentPage].pet.specie != KindPet.None.value() && listPets[pagerState.currentPage].pet.specie != KindPet.Other.value()) {
                    breedLauncher.launch(
                        SelectBreedActivity.newInstance(
                            context as Activity,
                            listPets[pagerState.currentPage].breeds,
                            listPets[pagerState.currentPage].pet.specie
                        )
                    )
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.estrella),
            contentDescription = "",
            modifier = Modifier.height(20.dp)
        )
        if (listPets[pagerState.currentPage].breeds.isEmpty()) {
            Text(
                text = "¿Cuál es su raza?",
                style = buttonTitleStyle.copy(fontSize = 14.sp, color = colorDisabled),
            )
        } else {
            listPets[pagerState.currentPage].breeds.forEach { breed ->
                CustomChip(breed.name) {
                    listPets[pagerState.currentPage] =
                        listPets[pagerState.currentPage].copy(breeds = listPets[pagerState.currentPage].breeds.filter { it.name != breed.name })
                }
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


fun KindPet.value(): String {
    return when (this) {
        KindPet.Dog -> "Perro"
        KindPet.Cat -> "Gato"
        KindPet.Other -> "Otro"
        KindPet.None -> "Ninguno"
    }
}

fun getKindPet(value: String?): KindPet {
    return when (value) {
        "Perro" -> KindPet.Dog
        "Gato" -> KindPet.Cat
        "Otro" -> KindPet.Other
        else -> KindPet.None
    }
}


class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

class CurrencyAmountInputVisualTransformation(
    private val fixedCursorAtTheEnd: Boolean = true,
    private val numberOfDecimals: Int = 2
) : VisualTransformation {

    private val symbols = DecimalFormat().decimalFormatSymbols

    override fun filter(text: AnnotatedString): TransformedText {
        val thousandsSeparator = symbols.groupingSeparator
        val decimalSeparator = symbols.decimalSeparator
        val zero = symbols.zeroDigit

        val inputText = text.text

        val intPart = inputText
            .dropLast(numberOfDecimals)
            .reversed()
            .chunked(3)
            .joinToString(thousandsSeparator.toString())
            .reversed()
            .ifEmpty {
                zero.toString()
            }

        val fractionPart = inputText.takeLast(numberOfDecimals).let {
            if (it.length != numberOfDecimals) {
                List(numberOfDecimals - it.length) {
                    zero
                }.joinToString("") + it
            } else {
                it
            }
        }

        val formattedNumber = intPart + decimalSeparator + fractionPart

        val newText = AnnotatedString(
            text = formattedNumber,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = if (fixedCursorAtTheEnd) {
            FixedCursorOffsetMapping(
                contentLength = inputText.length,
                formattedContentLength = formattedNumber.length
            )
        } else {
            MovableCursorOffsetMapping(
                unmaskedText = text.toString(),
                maskedText = newText.toString(),
                decimalDigits = numberOfDecimals
            )
        }

        return TransformedText(newText, offsetMapping)
    }

    private class FixedCursorOffsetMapping(
        private val contentLength: Int,
        private val formattedContentLength: Int,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = formattedContentLength
        override fun transformedToOriginal(offset: Int): Int = contentLength
    }

    private class MovableCursorOffsetMapping(
        private val unmaskedText: String,
        private val maskedText: String,
        private val decimalDigits: Int
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when {
                unmaskedText.length <= decimalDigits -> {
                    maskedText.length - (unmaskedText.length - offset)
                }

                else -> {
                    offset + offsetMaskCount(offset, maskedText)
                }
            }

        override fun transformedToOriginal(offset: Int): Int =
            when {
                unmaskedText.length <= decimalDigits -> {
                    max(unmaskedText.length - (maskedText.length - offset), 0)
                }

                else -> {
                    offset - maskedText.take(offset).count { !it.isDigit() }
                }
            }

        private fun offsetMaskCount(offset: Int, maskedText: String): Int {
            var maskOffsetCount = 0
            var dataCount = 0
            for (maskChar in maskedText) {
                if (!maskChar.isDigit()) {
                    maskOffsetCount++
                } else if (++dataCount > offset) {
                    break
                }
            }
            return maskOffsetCount
        }
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


@Composable
fun BasicEditTextField(
    maxWidth: Dp, modifier: Modifier,
    textStyle: TextStyle = mediumTitleStyle.copy(
        color = colorMediumBlue,
        fontSize = 30.sp,
        textAlign = TextAlign.Center
    ),
    iconSize: Dp = 27.dp,
    value: String,
    edit: () -> Unit = {}
) {
    var textState by remember { mutableStateOf(TextFieldValue(value)) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = textState,
            {
                textState = it
            },
            textStyle = textStyle,
            modifier = Modifier
                .widthIn(max = maxWidth),
            singleLine = true,
            readOnly = true
        )
        Image(
            painter = painterResource(id = R.drawable.ic_edit_new),
            contentDescription = "",
            modifier = Modifier
                .width(iconSize)
                .clickable {
                    edit()
                })
    }
}

@Composable
fun CustomDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .width(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }

            }
        }
    }
}