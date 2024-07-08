package com.pe.mascotapp.vistas.fragments.stepRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.colorYellow
import com.pe.mascotapp.skyBlue
import com.pe.mascotapp.titleStyle

class BreedPet(
    val category: BreedCategory,
    val name: String
)

enum class BreedCategory {
    INDEX,
    TYPE
}

@Composable
@Preview
fun SelectBreedPetsScreen() {
    val breedPets by remember {
        mutableStateOf((dogBreed.value.plus(catsBreed.value)).sorted())
    }

    val indexedList = mutableListOf<BreedPet>()

    val index = mutableListOf<String>()

    breedPets.groupBy { it.first() }.forEach { (initial, words) ->
        indexedList.add(BreedPet(BreedCategory.INDEX, initial.uppercaseChar().toString()))
        index.add(initial.uppercaseChar().toString())
        indexedList.addAll(words.map { BreedPet(BreedCategory.TYPE, it) })
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 29.dp, end = 9.dp, top = 24.dp, bottom = 11.dp)
            ) {
                Text(
                    text = "Selecciona su raza",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 11.dp, bottom = 26.dp)
            ) {
                SearchAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0XFFF2F2F2))
                )
            }

            Row(
                modifier = Modifier
                    .weight(2F)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(2F)
                        .padding(start = 29.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    items(indexedList) {
                        BreedPetItem(it)
                        Divider(modifier = Modifier.padding(top = 25.dp))
                    }
                }
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(index) {
                        Text(text = it, style = boldTitleStyle.copy(color = colorYellow))
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(skyBlue)
                    .padding(horizontal = 27.94.dp, vertical = 11.66.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Limpiar Selección",
                    style = boldTitleStyle.copy(fontSize = 15.sp, color = Color.White)
                )
                Text(
                    text = "0 Seleccionados",
                    style = titleStyle.copy(fontSize = 15.sp, color = Color.White)
                )
            }
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.69.dp),
                onClick = { /*TODO*/ },
                content = {
                    Text(text = "Continuar", style = buttonTitleStyle.copy(fontSize = 27.sp)) },
                shape = RoundedCornerShape(0.dp)
            )
        }

    }
}

@Composable
fun BreedPetItem(breedPet: BreedPet) {
    val textStyle =
        if (breedPet.category == BreedCategory.INDEX) boldTitleStyle.copy(fontSize = 20.sp) else titleStyle.copy(
            fontSize = 20.sp
        )
    Text(text = breedPet.name, style = textStyle)
}

val dogBreed = mutableStateOf(
    listOf(
        "Abyssinian",
        "American Bobtail",
        "American Curl",
        "American Shorthair",
        "American Wirehair",
        "Balinese",
        "Bengal",
        "Birman",
        "Bombay",
        "British Shorthair",
        "Burmese",
        "Burmilla",
        "Chartreux",
        "Chausie",
        "Cornish Rex",
        "Devon Rex",
        "Egyptian Mau",
        "European Shorthair",
        "Exotic Shorthair",
        "Havana Brown",
        "Himalayan",
        "Japanese Bobtail",
        "Javanese",
        "Korat",
        "LaPerm",
        "Maine Coon",
        "Manx",
        "Munchkin",
        "Norwegian Forest Cat",
        "Ocicat",
        "Oriental",
        "Persian",
        "Peterbald",
        "Pixie-bob",
        "Ragamuffin",
        "Ragdoll",
        "Russian Blue",
        "Savannah",
        "Scottish Fold",
        "Selkirk Rex",
        "Siamese",
        "Siberian",
        "Singapura",
        "Snowshoe",
        "Somali",
        "Sphynx",
        "Tonkinese",
        "Toyger",
        "Turkish Angora",
        "Turkish Van"
    )
)


val catsBreed =
    mutableStateOf(
        listOf(
            "Alaskan Klee Kai",
            "Alaskan Husky",
            "Alaskan Malamute",
            "Pastor Alemán",
            "Labrador Retriever",
            "Golden Retriever",
            "Beagle",
            "Bulldog Francés",
            "Bulldog Inglés",
            "Poodle (Caniche)",
            "Rottweiler",
            "Boxer",
            "Chihuahua",
            "Dachshund",
            "Shih Tzu",
            "Pug",
            "Doberman",
            "Shiba Inu",
            "Border Collie",
            "Great Dane (Gran Danés)",
            "Yorkshire Terrier",
            "Pomeranian",
            "Cavalier King Charles Spaniel",
            "Australian Shepherd",
            "Bichon Frisé",
            "Siberian Husky",
            "Maltese",
            "Cocker Spaniel",
            "Boston Terrier",
            "Pembroke Welsh Corgi",
            "Akita Inu",
            "Bernese Mountain Dog",
            "Basset Hound",
            "Staffordshire Bull Terrier",
            "Saint Bernard",
            "Samoyed",
            "American Pit Bull Terrier",
            "Airedale Terrier",
            "Afghan Hound",
            "Greyhound",
            "Whippet",
            "Basenji",
            "Bullmastiff",
            "Cane Corso",
            "Dalmatian",
            "Irish Wolfhound",
            "Leonberger",
            "Miniature Schnauzer",
            "Papillon",
            "Scottish Terrier"
        )
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(changeState: () -> Unit = { }, modifier: Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Surface(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.background,
    ) {
        TextField(
            singleLine = true,
            modifier = modifier,
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text(
                    text = "Buscar",
                    modifier = Modifier.alpha(ContentAlpha.medium),
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "")
            },
            trailingIcon =
            {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        text = ""
                        return@IconButton
                    }
                    changeState()
                }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "close icon")
                }
            },
            colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
    }
}
