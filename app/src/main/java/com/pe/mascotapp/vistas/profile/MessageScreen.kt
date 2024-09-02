import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorDisabled
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.vistas.HorizontalLine
import com.pe.mascotapp.vistas.profile.ChatActivity

@Preview
@Composable
fun MessagesScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 21.dp, top = 32.dp, bottom = 11.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedButton(
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(colorHeader)
                ) {
                    IconButton(onClick = {     (context as? Activity)?.finish()}) {
                        Icon(painter = painterResource(R.drawable.baseline_arrow_back_ios_24), contentDescription = "Back", tint = colorPrimary)
                    }
                }
                Text(
                    text = "Mensajes",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
/*            TopAppBar(
                title = { Text("Mensajes", style = MaterialTheme.typography.bodyLarge) },
                navigationIcon = {
                    IconButton(onClick = { *//* Acción de regreso *//* }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFD8E8F2)
                )
            )*/

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar()

            Spacer(modifier = Modifier.height(16.dp))

            MessagesList()
        }
    }
}

@Composable
fun SearchBar() {
    val searchText = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0XFFF2F2F2), RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = colorDisabled
            )
            BasicTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                decorationBox = { innerTextField ->
                    if (searchText.value.isEmpty()) {
                        Text("Buscar", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun MessagesList() {
    val messages = listOf(
        "Tenemos una propuesta perfecta para ti. La veterinaria Big Pet está regalando...",
        "Tenemos una propuesta perfecta para ti. La veterinaria Big Pet está regalando...",
        "Tenemos una propuesta perfecta para ti. La veterinaria Big Pet está regalando...",
        "Tenemos una propuesta perfecta para ti. La veterinaria Big Pet está regalando..."
    )

    Column {
        messages.forEach { message ->
            MessageItem(
                title = "Qunay",
                message = message,
                time = "9:40 AM"
            )
            HorizontalLine()
        }
    }
}

@Composable
fun MessageItem(title: String, message: String, time: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val intent = Intent(context, ChatActivity::class.java)
                context.startActivity(intent)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_qunay_default), // Cambia a tu imagen
            contentDescription = title,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(colorMediumBlue),
            contentScale = ContentScale.Inside
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = title,
                    style = boldTitleStyle.copy(fontSize = 17.sp, color = colorPrimary),
                    modifier = Modifier.weight(1f) // Add weight to the first Text
                )
                Text(
                    text = time,
                    style = buttonTitleStyle.copy(fontSize = 15.sp, color = Color(0x993C3C43)),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = buttonTitleStyle.copy(fontSize = 14.sp, color = Color(0x993C3C43)),
                maxLines = 2
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Top)
        )
    }
}