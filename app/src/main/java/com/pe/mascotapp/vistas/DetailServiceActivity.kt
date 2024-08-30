package com.pe.mascotapp.vistas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.vistas.fragments.DetailServiceFragmentState


class DetailServiceActivity : AppCompatActivity() {

    var tabLayout:TabLayout ?= null
    var viewPager:ViewPager ?= null
    var btnContactar:Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_service_activity)
        tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        viewPager = findViewById<ViewPager>(R.id.viewPStep)
        btnContactar = findViewById<Button>(R.id.btnContactar)

        viewPager!!.adapter = DetailServiceFragmentState(supportFragmentManager, this);
        tabLayout!!.setupWithViewPager(viewPager)

        btnContactar!!.setOnClickListener {
            openWhatsappContact("+51969928064")
        }

    }

    fun openWhatsappContact(number: String) {
        val uri = Uri.parse("smsto:$number")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(i, ""))
    }
}