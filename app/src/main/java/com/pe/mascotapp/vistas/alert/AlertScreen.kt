package com.pe.mascotapp.vistas.alert

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pe.mascotapp.R
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorHorizontal
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.mediumTitleStyle
import com.pe.mascotapp.textFieldTextStyle
import com.pe.mascotapp.vistas.HorizontalLine
import kotlinx.coroutines.launch


@Composable
fun AlertScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val alerts = listOf(
        AlertItem(
            imageRes = "https://static01.nyt.com/images/2024/01/16/multimedia/16xp-dog-01-lchw/16xp-dog-01-lchw-videoSixteenByNineJumbo1600.jpg",
            message = "Faltan 3 días para tu cita con el veterinario, no te olvides de llevar tus análisis",
            isRecent = true,
            location = "La Molina - Av. Precursores 1554"
        ),
        AlertItem(
            imageRes = "https://www.purina.es/sites/default/files/2022-12/Razas%20de%20perros%20peque%C3%B1os-01.jpg",
            message = "Recuerda darle su medicina a tu mascota",
            isRecent = true,
            location = "La Molina - Av. Precursores 1554"
        ),
        AlertItem(
            imageRes = "https://www.telegraph.co.uk/content/dam/news/2023/06/10/TELEMMGLPICT000296384999_16864028803870_trans_NvBQzQNjv4BqrCS9JVgwgb8GODK1xmD4xlHwtdpQwyNje2OyIL7x97s.jpeg",
            message = "Falta 3 días para tu cita con el especialista en profilaxis. Recuerda llevar tus documentos",
            isRecent = false,
            location = "La Molina - Av. Precursores 1554"
        ),
        AlertItem(
            imageRes = "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg",
            message = "No te olvides de la cita para el corte de pelo de tu mascota",
            isRecent = false,
            location = "La Molina - Av. Precursores 1554"
        ),
        AlertItem(
            imageRes = "https://www.petdarling.com/wp-content/uploads/2020/05/pastor-aleman-caracteristicas-2.jpg",
            message = "Recuerda comprar la comida de tu mascota",
            isRecent = false,
            location = "La Molina - Av. Precursores 1554"
        ),
        AlertItem(
            imageRes = "https://t1.ea.ltmcdn.com/es/posts/8/0/1/las_razas_de_perros_mas_grandes_del_mundo_5108_orig.jpg",
            message = "Tienes una nueva solicitud de servicio",
            isRecent = false,
            location = "La Molina - Av. Precursores 1554"
        )
    )
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 29.dp, end = 21.dp, top = 32.dp, bottom = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Alertas",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
                ElevatedButton(
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(colorHeader)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close), // Reemplaza con el ícono de cerrar que tengas
                        contentDescription = "Cerrar",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                (context as? Activity)?.finish()
                            }, tint = colorPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Header Section
            /*            Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Alertas",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                ),
                                color = Color(0xFF2C3E50)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close), // Reemplaza con el ícono de cerrar que tengas
                                contentDescription = "Cerrar",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { *//* Acción para cerrar *//* }
                )
            }*/

            //           Spacer(modifier = Modifier.height(8.dp))

            // List of Alerts
            val recentAlerts = alerts.filter { it.isRecent }
            val pastAlerts = alerts.filter { !it.isRecent }
            Column {
                recentAlerts.forEach { alert ->
                    AlertItemScreen(alert, onAlertDeleted = {  })
                }
                Text(
                    text = "Anteriores:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                pastAlerts.forEach { alert ->
                    AlertItemScreen(alert,onAlertDeleted = { /*TODO*/ })
                    HorizontalLine()
                }
            }
        }
    }
}

data class AlertItem(
    val imageRes: String,
    val message: String,
    val isRecent: Boolean,
    val location: String
)

@Composable
fun ActionMenu(
    item: AlertItem, // The item for which the menu is displayed
    onDelete: (AlertItem) -> Unit, // Callback to delete the item
    onUpdate: (AlertItem) -> Unit, // Callback to update the item
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier// Callback to dismiss the menu
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Select Action",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onDelete(item); onDismiss() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onUpdate(item); onDismiss() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onDismiss() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Cancel", color = Color.Black)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlertItemScreen(itemAlert: AlertItem, onAlertDeleted: (AlertItem) -> Unit) {
    var selectedAlert by remember { mutableStateOf<AlertItem?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true )
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (itemAlert.isRecent) colorHorizontal else Color(0xFFFFFFFF),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 18.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = itemAlert.imageRes,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(
                text = itemAlert.message,
                style = buttonTitleStyle.copy(fontSize = 15.sp, color = colorDisabled),
                color = colorDisabled
            )
        }
        Row(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.Top)
                .padding(top = 4.dp)
                .clickable {
                    openBottomSheet = true
                    selectedAlert = itemAlert
                }, // Adjust the size as needed
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(4.dp) // Adjust the size of each circle
                        .clip(CircleShape)
                        .background(colorPrimary) // Set your desired color
                )
            }
        }
    }

    if (openBottomSheet) {
        if (selectedAlert != null) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = sheetState,
            ) {
                // Bottom sheet content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Alert Details")
                    // ... display alert details here ...

                    Button(onClick = { openBottomSheet = false }) {
                        Text("Close")
                    }
                }
            }
        }
    }
    if (openBottomSheet) {
        selectedAlert?.let {
            CustomAlertModal(
                onDismissRequest = { openBottomSheet = false },
                sheetState = sheetState,
                onDeleteAlert = {
                    onAlertDeleted(it)
                    openBottomSheet = false
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onDisableNotifications = { /*TODO*/ },
                selectedAlert = it
            )
        }
    }
}

@ExperimentalMaterial3Api
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CustomAlertModal(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onDeleteAlert: () -> Unit,
    onDisableNotifications: () -> Unit,
    selectedAlert: AlertItem,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen circular
            GlideImage(
                model =selectedAlert.imageRes,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = selectedAlert.message,
                textAlign = TextAlign.Center,
                style = buttonTitleStyle.copy(fontSize = 16.sp),
                color = colorDisabled
            )

            // Ubicación
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = colorMediumBlue
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = selectedAlert.location,
                    style = textFieldTextStyle.copy(fontSize = 12.sp),
                    color = colorMediumBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Eliminar alerta
            Button(
                onClick = {
                    onDeleteAlert() // Acción para eliminar alerta
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(0.9f).clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(Icons.Default.Clear, contentDescription = null, tint = colorPrimary,  modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Eliminar esta alerta",
                    style = mediumTitleStyle.copy(color = colorPrimary, fontSize = 18.sp),
                    color = colorDisabled
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Desactivar notificaciones
            Button(
                onClick = {
                    onDisableNotifications() // Acción para desactivar notificaciones
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth(0.9f).clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(20.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    tint = colorPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Desactivar notificaciones",
                    style = mediumTitleStyle.copy(color = colorPrimary,fontSize = 18.sp),
                    color = colorDisabled
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAlertScreen() {
    AlertScreen()
}