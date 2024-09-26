package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.modelos.Usuario
import com.pe.mascotapp.vistas.CarosuelRegisterActivity
import java.util.Calendar

// Replace with your app's package name

@Preview
@Composable
fun StepOneScreen(   usuario: Usuario = Usuario(),  onSiguienteClick: (Usuario) -> Unit = {}) {
    val scrollState = rememberScrollState()
    val ctx = LocalContext.current
    val currentStep = remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf(usuario.name) }
    var email by remember { mutableStateOf(usuario.email) }
    var phone by remember { mutableStateOf(usuario.numPhone) }
    var birthday by remember { mutableStateOf(usuario.birthdate) }
    var password by remember { mutableStateOf(usuario.pass) }
    var confirmPassword by remember { mutableStateOf(usuario.pass) }
    var termsAccepted by remember { mutableStateOf(usuario.terms) }
    var validationMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    fun validateInputs() :Boolean {
        return (name.isNotBlank() && phone.isNotBlank()
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && birthday.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
                && password == confirmPassword
                && termsAccepted).apply {
                 if (!this){
                     validationMessage = when {
                         name.isBlank() -> "Ingresa tu nombre"
                         email.isBlank() -> "Ingresa tu email"
                         !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Ingresa un email válido"
                         phone.isBlank() -> "Ingresa tu teléfono"
                         birthday.isBlank() -> "Ingresa tu fecha de nacimiento"
                         password.isBlank() -> "Ingresa tu contraseña"
                         password != confirmPassword -> "Las contraseñas no coinciden"
                         !termsAccepted -> "Acepta los términos y condiciones"
                         else -> "" // All valid
                     }
                 }
        }
    }
    fun setCalendar(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            ctx,
            androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                birthday = "${
                    dayOfMonth.toString().padStart(2, '0')
                }${(month + 1).toString().padStart(2, '0')}${
                    year.toString().padStart(4, '0')
                }"

            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
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
                            if (!validateInputs()) {
                                Toast.makeText(
                                    ctx,
                                    validationMessage,Toast.LENGTH_SHORT
                                ).show()
                                return@PrimaryButton
                                Log.d("TAG", "validateInputs: " + validationMessage)
                            }
                            val updatedUsuario = usuario.copy(
                                name = name,
                                email = email,
                                numPhone = phone,
                                birthdate = birthday,
                                pass = password,
                                terms = termsAccepted
                            )
                            (ctx as? CarosuelRegisterActivity)?.updateUsuario(updatedUsuario)
                            (ctx as? CarosuelRegisterActivity)?.nextStep()
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
                            (ctx as? CarosuelRegisterActivity)?.onBackPressed()
                        }) {
                        Text(text = "volver", style = buttonTitleStyle, color = colorPrimary)
                    }
                }

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
                    .padding(bottom = 20.dp),
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
                        keyBoarType = KeyboardType.Email,
                        onValueChange = {
                            email = it
                        },
                        label = stringResource(R.string.label_email)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CustomTextField(
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                            leadingIcon = painterResource(id = R.drawable.telefono),
                            value = phone,
                            onValueChange = { newValue->
                                val filteredValue = newValue.filter { it.isDigit() }
                                if (filteredValue.length <= 9) {
                                    phone=filteredValue
                                }
                            },
                            keyBoarType = KeyboardType.Phone,
                            label = stringResource(id = R.string.label_phone)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        CustomTextField(
                            Modifier
                                .weight(1F)
                                .fillMaxHeight()
                                .clickable {
                                    setCalendar()
                                },
                            leadingIcon = painterResource(id = R.drawable.edad),
                            value = birthday,
                            keyBoarType = KeyboardType.Number,
                            visualTransformation = DateTransformation(),
                            onValueChange = {
                                birthday = it
                                if (!it.contains(".") && !it.contains(",") && !it.contains(" ")) {
                                    birthday = it
                                }
                            },
                            label = stringResource(id = R.string.label_born),
                            enabled = false,
                            leadingIconOnClick = {
                                setCalendar()
                            }
                        )
                    }
                    CustomTextField(
                        Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.candado),
                        value = password,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = {
                            password = it
                        },
                        label = stringResource(id = R.string.label_password),
                        trailingIcon = {
                            Icon(
                                painter = if (passwordVisible) painterResource(id = R.drawable.password_show) else painterResource(id = R.drawable.password_hide),
                                contentDescription = null,
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        },
                    )
                    CustomTextField(
                        Modifier.fillMaxWidth(),
                        leadingIcon = painterResource(id = R.drawable.candado),
                        value = confirmPassword,
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onValueChange = {
                            confirmPassword = it
                        },
                        label = stringResource(id = R.string.label_confirm_password),
                        trailingIcon = {
                            Icon(
                                painter = if (confirmPasswordVisible) painterResource(id = R.drawable.password_show) else painterResource(id = R.drawable.password_hide),
                                contentDescription = null,
                                modifier = Modifier.clickable { confirmPasswordVisible = !confirmPasswordVisible }
                            )
                        }
                    )
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { termsAccepted = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorMediumBlue // Replace with your actual color resource
                            )
                        )
                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.label_terms)),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://intothewildescapes.com/pet-terms"))
                                ctx.startActivity(intent)
                            },
                            style = TextStyle(
                                color = colorPrimary,
                                textDecoration = TextDecoration.None
                            )
                        )
                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun WebViewScreen() {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl("https://intothewildescapes.com/pet-terms")
        }
    })
}


