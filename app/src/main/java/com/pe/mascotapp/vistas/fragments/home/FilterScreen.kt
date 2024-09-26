package com.pe.mascotapp.vistas.fragments.home


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.colorGrisTittle
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.descriptionTextStyle
import com.pe.mascotapp.semiBoldTitleStyle
import com.pe.mascotapp.titleStyle


@Composable
fun BoxWithText(
    icon: ImageVector?,
    text: String,
    borderColor: Color,
    onClick: () -> Unit,
) {
    val iconColor = colorDisabled
    val colorText = colorDisabled
    val styleText = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorText)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) { // Check if icon is not null
                Icon(
                    imageVector = icon,
                    contentDescription = "Box Icon",
                    tint = borderColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = styleText,
                color = borderColor
            )
        }
    }
}

@Composable
fun ItemGrid(
    items: List<BoxItem>,
    itemsPerRow: Int = 3
) {
    val selectedItems = remember { mutableStateListOf<Int>() }
    val rows = (items.size + itemsPerRow - 1) / itemsPerRow
    Column {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0 until itemsPerRow) {
                    val index = row * itemsPerRow + col
                    if (index < items.size) {
                        val item = items[index]
                        val isSelected = selectedItems.contains(index)
                        val borderColor = if (isSelected) colorPrimary else colorDisabled
                        BoxWithText(
                            icon = item.icon,
                            text = item.text,
                            borderColor = borderColor,
                            onClick = {
                                if (isSelected) {
                                    selectedItems.remove(index)
                                } else {
                                    selectedItems.add(index)
                                }
                            }
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
    // You can access the selectedItems list here
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun FilterScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val selectedDistricts = remember { mutableStateListOf<String>() }
    val districts = listOf(
        "Jesús María", "Breña", "San Isidro", "Miraflores",
        "La Molina", "Centro de Lima", "Ate", "Surco", "Barranco", "La Victoria"
    )

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 21.dp, top = 32.dp, bottom = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
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
                    text = "Pet House",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Limpiar",
                        style = titleStyle.copy(fontSize = 14.sp, color = colorPrimary)
                    )
                }
            }
        },
        bottomBar = {
            Button(
                shape = RoundedCornerShape(0.dp),
                onClick = { /* Handle accept */ },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    colorMediumBlue,// Set the desired blue color

                ),
            ) {
                Text(
                    text = "Aceptar",
                    style = descriptionTextStyle.copy(fontSize = 23.sp, color = Color.White)
                )
            }
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(Color.White)
        ) {

            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    // Filter options
                    Text(
                        text = "Filtrar por",
                        style = boldTitleStyle.copy(fontSize = 18.sp, color = colorPrimary),
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val filterTtems = listOf(
                        BoxItem(
                            ImageVector.vectorResource(id = R.drawable.ic_24hr),
                            "Atiende 24 Horas"
                        ),
                        BoxItem(
                            ImageVector.vectorResource(id = R.drawable.ic_start),
                            "Más  de 4.5"
                        ),
                        BoxItem(
                            ImageVector.vectorResource(id = R.drawable.ic_promos),
                            "Promociones"
                        ),
                        BoxItem(
                            ImageVector.vectorResource(id = R.drawable.ic_prices),
                            "Menos Precio"
                        ),
                        BoxItem(
                            ImageVector.vectorResource(id = R.drawable.ic_car_pick_up),
                            "Recojo a domicilio"
                        )
                    )
                    ItemGrid(items = filterTtems, itemsPerRow = 2) // 3 items per row

                    Spacer(modifier = Modifier.height(16.dp))

                    // Location dropdown
                    Text(
                        text = "Ubicación",
                        style = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorGrisTittle),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DistrictDropdown(districts, selectedDistricts)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pet type section (example with just a placeholder text)
                    Text(
                        text = "Por tipo de mascota:",
                        style = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorGrisTittle)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val typesPetsItems = listOf(
                        BoxItem(ImageVector.vectorResource(id = R.drawable.perro), "Perros"),
                        BoxItem(ImageVector.vectorResource(id = R.drawable.gato), "Gatos"),
                        BoxItem(ImageVector.vectorResource(id = R.drawable.llama), "Otros"),
                    )
                    ItemGrid(items = typesPetsItems, itemsPerRow = 2) // 3 items per row

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pet type section (example with just a placeholder text)
                    Text(
                        text = "Por tipo de cuidados",
                        style = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorGrisTittle)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val takeCareItems = listOf(
                        BoxItem(null, "Administran medicinas"),
                        BoxItem(null, "Cuentan con cuidados medicos"),
                        BoxItem(null, "Cuidado a mascotas mayores"),
                    )
                    ItemGrid(items = takeCareItems, itemsPerRow = 1) // 3 items per row

                }
            }

        }

    }
}

@Composable
fun FilterChip(text: String) {
    Surface(
        modifier = Modifier.clickable { /* Handle chip click */ },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFEAEAEA)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictDropdown(districts: List<String>, selectedDistricts: MutableList<String>) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.height(50.dp)
    ) {
/*               Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(0.85f)
                .background(Color(0xFFF6F6F6))
                .border(2.dp, colorDisabled, RoundedCornerShape(16.dp))
                .padding(start = 8.dp),
        ) {
            Row(
                modifier = Modifier.padding(4.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (selectedDistricts.isEmpty()) "Agregar distritos" else selectedDistricts.joinToString(),
                    textAlign = TextAlign.Center,
                    style = boldTitleStyle.copy(colorDisabled, fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = {
                    ExposedDropdownMenuDefaults.(expanded)
                }) {
                    Icon(tint = colorDisabled, painter = painterResource(id = R.drawable.ic_arrow), contentDescription = "dropdownMenu")
                }
            }

        }*/
        OutlinedTextField(
            value = if (selectedDistricts.isEmpty()) "Agregar distritos" else selectedDistricts.joinToString(),onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(

                focusedTextColor =colorDisabled,
                unfocusedTextColor = colorDisabled,
                disabledTextColor = colorDisabled,
                focusedContainerColor = Color(0xFFF6F6F6),
                unfocusedContainerColor = Color(0xFFF6F6F6),
                disabledContainerColor = Color(0xFFF6F6F6),
                errorContainerColor = Color(0xFFF6F6F6),
                cursorColor = colorPrimary,
                focusedBorderColor = colorPrimary,
                unfocusedBorderColor = colorDisabled,
                focusedLabelColor = colorPrimary,
            ),textStyle = boldTitleStyle.copy(color = colorDisabled, fontSize = 14.sp), // Set text style
            shape = RoundedCornerShape(16.dp), // Set rounded corners
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(start = 8.dp).menuAnchor()
        )
/*        BasicTextField(
            value = if (selectedDistricts.isEmpty()) "Agregar distritos" else selectedDistricts.joinToString(),
            onValueChange = {  },
            modifier =  Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(0.85f)
                .background(Color(0xFFF6F6F6))
                .border(2.dp, colorDisabled, RoundedCornerShape(16.dp))
                .padding( 8.dp), // Add padding here if needed
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp) // Control padding here
                ) {
                    if (selectedDistricts.isEmpty()) {
                        Text(
                            text = "",
                            color =Color.Gray
                        )
                    }
                    innerTextField()
                }
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },

        )*/
/*        OutlinedTextField(
            value = if (selectedDistricts.isEmpty()) "Agregar distritos" else selectedDistricts.joinToString(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )*/
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            districts.forEach { district ->
                DropdownMenuItem(
                    onClick = {
                        if (selectedDistricts.contains(district)) {
                            selectedDistricts.remove(district)
                        } else {
                            selectedDistricts.add(district)
                        }
                        expanded = false
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = selectedDistricts.contains(district),
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = district)
                        }
                    }
                )
            }
        }
    }
}

data class BoxItem(val icon: ImageVector? = null, val text: String)