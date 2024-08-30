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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.semiBoldTitleStyle
import com.pe.mascotapp.titleStyle


@Composable
fun FilterScreen() {


    val items = listOf(
        Pair(Icons.Filled.Home, "Home"),
        Pair(Icons.Filled.Settings, "Settings"),
        Pair(Icons.Filled.Person, "Profile"),
        Pair(Icons.Filled.Favorite, "Favorites"),
        Pair(Icons.Filled.Search, "Search"),
        Pair(Icons.Filled.Email, "Email")
    )
    ItemGrid(items = items, itemsPerRow = 2) // 3 items per row

}

@Composable
fun BoxWithText(
    icon: ImageVector,
    text: String,
    borderColor: Color,
    onClick: () -> Unit
) {
    val iconColor = colorDisabled
    val colorText = colorDisabled
    val styleText = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorText)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, contentDescription = "Box Icon",
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = styleText
            )
        }
    }
}

@Composable
fun ItemGrid(
    items: List<Pair<ImageVector, String>>,
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
                        val borderColor = if (isSelected) Color.Red else Color.Gray
                        BoxWithText(
                            icon = item.first,
                            text = item.second,
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
fun FilterScreen2() {
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
                        style = titleStyle.copy(fontSize = 14.sp,color = colorPrimary,)
                    )
                }
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

                    val items = listOf(
                        Pair(Icons.Filled.Home, "Home"),
                        Pair(Icons.Filled.Settings, "Settings"),
                        Pair(Icons.Filled.Person, "Profile"),
                        Pair(Icons.Filled.Favorite, "Favorites"),
                        Pair(Icons.Filled.Search, "Search"),
                        Pair(Icons.Filled.Email, "Email")
                    )
                    ItemGrid(items = items, itemsPerRow = 2) // 3 items per row

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
                    ItemGrid(items = items, itemsPerRow = 2) // 3 items per row

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pet type section (example with just a placeholder text)
                    Text(
                        text = "Por tipo de cuidados",
                        style = semiBoldTitleStyle.copy(fontSize = 16.sp, color = colorGrisTittle)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ItemGrid(items = items, itemsPerRow = 2) // 3 items per row

                }
            }


            // Accept button
            Button(
                onClick = { /* Handle accept */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = "Aceptar")
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
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = if (selectedDistricts.isEmpty()) "Agregar distritos" else selectedDistricts.joinToString(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
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
                                onCheckedChange = null // Null as we handle clicks on the whole row
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = district)
                        }
                    })
            }
        }
    }
}