package com.pe.mascotapp.vistas.fragments.stepRegister

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ContentAlpha
import com.pe.mascotapp.R
import com.pe.mascotapp.boldTitleStyle
import com.pe.mascotapp.buttonTitleStyle
import com.pe.mascotapp.caprasimoTitleStyle
import com.pe.mascotapp.colorHeader
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.colorYellow
import com.pe.mascotapp.skyBlue
import com.pe.mascotapp.titleStyle
import com.pe.mascotapp.vistas.fragments.stepRegister.SelectBreedActivity.Companion.BUNDLE_BREED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedPetEntity(
    val category: BreedCategory,
    val name: String,
    var isSelected: Boolean = false
) : Parcelable

enum class BreedCategory {
    INDEX,
    TYPE
}

@Composable
fun SelectBreedPetsScreen(listBreed: ArrayList<BreedPetEntity>, kindPet: KindPet) {
    val kindListBreed = when (kindPet) {
        KindPet.Dog -> dogBreed.value
        KindPet.Cat -> catsBreed.value
        else -> emptyList()
    }
    val breedPets by remember {
        mutableStateOf(kindListBreed.sorted())
    }
    val context = LocalContext.current

    val totalItems = remember { mutableStateListOf<BreedPetEntity>() }

    val indexItems = remember { mutableStateListOf<BreedPetEntity>() }

    val interactionSource = remember { MutableInteractionSource() }

    breedPets.groupBy { it.first() }.forEach { (initial, words) ->
        totalItems.add(BreedPetEntity(BreedCategory.INDEX, initial.uppercaseChar().toString()))
        indexItems.add(BreedPetEntity(BreedCategory.INDEX, initial.uppercaseChar().toString()))
        totalItems.addAll(words.map { item ->
            BreedPetEntity(BreedCategory.TYPE, item, listBreed.find { it.name == item } != null)
        })
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }
    var indexRecent = 0
    fun getCountItemSelected(): Int {
        return totalItems.count { it.category == BreedCategory.TYPE && it.isSelected }
    }

    fun searchBreed(query: String) {
        coroutineScope.launch {
            if (query.isEmpty()) return@launch
            var indexFirst =
                totalItems.indexOfFirst {
                    it.name.contains(query, true) && it.name.first().equals(
                        query.first(),
                        ignoreCase = true
                    )
                }
            if (indexFirst == -1) {
                indexFirst = totalItems.indexOfFirst {
                    it.name.contains(query, true)
                }
            }
            if (indexFirst != -1) {
                val letter = totalItems[indexFirst].name.first().uppercaseChar()
                val indexLetter = indexItems.indexOfFirst { it.name == letter.toString() }

                withContext(Dispatchers.Main){
                    listState.animateScrollToItem(indexFirst)
                    indexItems[indexRecent] = indexItems[indexRecent].copy(isSelected = false)
                    indexItems[indexLetter] = indexItems[indexLetter].copy(isSelected = true)
                    indexRecent = indexLetter
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .background(colorHeader)
                    .fillMaxWidth()
                    .padding(start = 29.dp, end = 21.dp, top = 24.dp, bottom = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Selecciona su raza",
                    style = caprasimoTitleStyle.copy(color = colorPrimary)
                )
                ElevatedButton(
                    contentPadding = PaddingValues(),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                        (context as? Activity)?.finish()
                    },
                    colors = ButtonDefaults.buttonColors(colorHeader)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_return),
                        contentDescription = "",
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 11.dp, bottom = 26.dp)
            ) {
                SearchAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0XFFF2F2F2)),
                    changeState = { query ->
                        searchBreed(query)
                    }
                )
            }

            Row(
                modifier = Modifier
                    .weight(2F)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(3F)
                        .padding(start = 29.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    itemsIndexed(totalItems) { index, item ->
                        BreedPetItem(item) {
                            totalItems[index] =
                                totalItems[index].copy(isSelected = !totalItems[index].isSelected)
                        }
                        Divider(modifier = Modifier.padding(top = 25.dp))
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .width(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(indexItems) { index, item ->
                        val fontSize = if (item.isSelected) {
                            30.sp
                        } else 20.sp
                        Text(
                            text = item.name,
                            style = boldTitleStyle.copy(
                                color = colorYellow,
                                fontSize = fontSize
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick =
                                    {
                                        searchBreed(indexItems[index].name)
                                    }),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            if (getCountItemSelected() > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(skyBlue)
                        .padding(horizontal = 27.94.dp, vertical = 11.66.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Limpiar Selección",
                        modifier = Modifier.clickable {
                            totalItems.forEachIndexed { index, breedPet ->
                                if (breedPet.isSelected) {
                                    totalItems[index] = breedPet.copy(isSelected = false)
                                }
                            }
                        },
                        style = boldTitleStyle.copy(fontSize = 15.sp, color = Color.White)
                    )
                    Text(
                        text = "${totalItems.count { it.isSelected }} Seleccionados",
                        style = titleStyle.copy(fontSize = 15.sp, color = Color.White)
                    )
                }
            }

            val ctx = LocalContext.current

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.69.dp),
                onClick = {
                    val resultIntent = Intent().apply {
                        putExtra(BUNDLE_BREED, totalItems.filter { it.isSelected }.toTypedArray())
                    }
                    (ctx as? Activity)?.setResult(RESULT_OK, resultIntent)
                    (ctx as? Activity)?.finish()
                },
                content = {
                    Text(text = "Continuar", style = buttonTitleStyle.copy(fontSize = 27.sp))
                },
                shape = RoundedCornerShape(0.dp)
            )
        }

    }

}

@Composable
fun BreedPetItem(breedPet: BreedPetEntity, onClick: () -> Unit) {
    val textStyle =
        if (breedPet.category == BreedCategory.INDEX) boldTitleStyle.copy(fontSize = 20.sp) else titleStyle.copy(
            fontSize = 20.sp
        )
    var modifier: Modifier = Modifier.fillMaxSize()
    if (breedPet.category != BreedCategory.INDEX) {
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick.invoke() }
    }
    Row(
        modifier = modifier
    ) {
        Text(
            text = breedPet.name,
            style = if (breedPet.isSelected) textStyle.copy(color = Color.White) else textStyle,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (breedPet.isSelected) skyBlue else Color.Transparent)
                .padding(horizontal = 10.dp)
        )
    }
}

val catsBreed = mutableStateOf(
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


val dogBreed =
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

@Composable
fun SearchAppBar(changeState: (query: String) -> Unit = { }, modifier: Modifier) {
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
                changeState(text)
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
