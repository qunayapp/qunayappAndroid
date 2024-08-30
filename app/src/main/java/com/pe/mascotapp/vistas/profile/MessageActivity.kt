package com.pe.mascotapp.vistas.profile

import MessagesScreen
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMessageBinding.inflate(layoutInflater)
        binding.composeViewMessage.setContent {
            MessagesScreen()
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