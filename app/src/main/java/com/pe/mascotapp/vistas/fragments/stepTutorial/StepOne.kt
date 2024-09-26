package com.pe.mascotapp.vistas.fragments.stepTutorial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.pe.mascotapp.R
import com.pe.mascotapp.colorMediumBlue
import com.pe.mascotapp.colorPrimary
import com.pe.mascotapp.databinding.FragmentTutorialOneBinding


class StepOne : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTutorialOneBinding.inflate(inflater, container, false)
        binding.composeViewTutorialOne.setContent {
            StepsTutorial(
                "Bienvenido a \n Qunay",
                "Esta es la primera app del Perú que ofrece un mundo de opciones para el bienestar de tu mejor amigo.",
                R.drawable.tutorial_1
            )
        }
        return binding.root
    }

    companion object {
        fun newInstance(): StepOne {
            val stepOne = StepOne()
            return stepOne
        }
    }
}

@Preview
@Composable
fun StepsTutorial(
    title: String = "Bienvenido a Qunay",
    description: String = "",
    image: Int = R.drawable.tutorial_1
) {

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_qunay),
                contentDescription = "Logo",
                modifier = Modifier.width(126.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id =image),
                contentDescription = "Tutorial Image",
                modifier = Modifier
                    .size(320.dp)
                    .wrapContentSize(Alignment.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.worksans_black)),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = colorPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                fontFamily = FontFamily(Font(R.font.worksans_regular)),
                fontSize = 16.sp,
                color = colorMediumBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            )
        }

    }
}