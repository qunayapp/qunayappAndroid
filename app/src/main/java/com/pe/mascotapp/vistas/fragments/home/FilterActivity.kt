package com.pe.mascotapp.vistas.fragments.home

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityFilterBinding.inflate(layoutInflater)
        binding.composeViewFilter.setContent {
            FilterScreen2()
        }
        setContentView(binding.root)
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ViewCompat.setOnApplyWindowInsetsListener(w.decorView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }
}