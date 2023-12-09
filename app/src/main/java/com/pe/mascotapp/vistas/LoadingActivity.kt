package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.pe.mascotapp.R
import com.pe.mascotapp.utils.Constantes
import com.pe.mascotapp.utils.Utils

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, CarosuelTutorialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }, 5000)
    }
}