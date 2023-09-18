package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.pe.mascotapp.R

class JourneyTipoOneThankYouActivity : AppCompatActivity() {

    var btnVolver:Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_tipo_one_thank_you)

        btnVolver = findViewById<View>(R.id.btnVolver) as Button

        btnVolver!!.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}