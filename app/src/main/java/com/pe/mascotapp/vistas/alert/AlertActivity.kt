package com.pe.mascotapp.vistas.alert

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity() {
    private var _binding: ActivityAlertBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlertBinding.inflate(layoutInflater)
        binding.composeViewAlert.setContent {
            AlertScreen()
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