package com.pe.mascotapp.vistas.fragments.stepRegister

import android.annotation.SuppressLint
import android.widget.Button
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.colorMediumBlue
import kotlinx.coroutines.launch

// Replace with your app's package name

@Preview
@Composable
fun StepOneScreen() {
    val scrollState = rememberScrollState()
    val currentStep = remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }

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
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Paso 1",
                        textAlign = TextAlign.Center,
                        style = boldTitleStyle,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Completa tus datos",
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = colorMediumBlue // Replace with your actual color resource
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StepsProgressBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 87.dp, end = 87.dp, top = 10.dp),
                        numberOfSteps = 2,
                        currentStep = currentStep.intValue
                    )
                    Image(
                        painter = painterResource(id = R.drawable.logo_qunay), // Replace with your actual image resource
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        alignment = Alignment.Center
                    )
                    Spacer(modifier  = Modifier.height(16.dp))
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.nombre_usuario),
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = "Nombre "
                    )
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.email),
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = stringResource(R.string.label_email, "New Year", 2021)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CustomTextField(
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            leadingIcon = painterResource(id = R.drawable.telefono),
                            value = phone,
                            onValueChange = {
                                phone = it
                            },
                            label = stringResource(id = R.string.label_phone)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        CustomTextField(
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            leadingIcon = painterResource(id = R.drawable.edad),
                            value = birthday,
                            onValueChange = {
                                birthday = it
                            },
                            label = stringResource(id = R.string.label_phone)
                        )
                    }
                    CustomTextField(
                        Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.candado),
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = stringResource(id = R.string.label_password)
                    )
                    CustomTextField(
                        Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.candado),
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                        },
                        label = stringResource(id = R.string.label_confirm_password)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { termsAccepted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorMediumBlue // Replace with your actual color resource
                            )
                        )
                        Text(
                            "Términos de uso y Política",
                            color = colorMediumBlue
                        ) // Replace with your actual color resource
                    }
                    // Removed optional section as it was marked for deletion
                    // ... Add a button for registration here
                }
            }
        }
    }
}

