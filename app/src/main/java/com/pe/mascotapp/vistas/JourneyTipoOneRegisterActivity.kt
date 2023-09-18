package com.pe.mascotapp.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Veterinaria
import com.pe.mascotapp.utils.Utils

class JourneyTipoOneRegisterActivity : AppCompatActivity() {

    var txtTitleVet:TextView ?= null
    var autoServicio:AutoCompleteTextView ?= null
    var calendarViewVet:CalendarView ?= null
    var autoSelectedFecha:AutoCompleteTextView ?= null
    var autoHorarios:AutoCompleteTextView ?= null
    var autoTipo:AutoCompleteTextView ?= null
    var autoDosis:AutoCompleteTextView ?= null
    var autoPeso:AutoCompleteTextView ?= null
    var autoPaciente:AutoCompleteTextView ?= null
    var edtEmail: TextInputEditText?= null
    var btnContinuar:Button ?= null
    var txtCancelar:TextView ?= null
    var edtSelectedFecha: TextInputLayout?= null
    var veterinaria:Veterinaria = Veterinaria()
    var lnlDetalleServicio:LinearLayout ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_tipo_one_register)

        veterinaria = intent.getSerializableExtra("OBJECT_VETERINARIA") as Veterinaria
        txtTitleVet = findViewById<View>(R.id.txtTitleVet) as TextView
        autoServicio = findViewById<View>(R.id.autoServicio) as AutoCompleteTextView
        calendarViewVet = findViewById<View>(R.id.calendarViewVet) as CalendarView
        autoSelectedFecha = findViewById<View>(R.id.autoSelectedFecha) as AutoCompleteTextView
        autoHorarios = findViewById<View>(R.id.autoHorarios) as AutoCompleteTextView
        autoTipo = findViewById<View>(R.id.autoTipo) as AutoCompleteTextView
        autoDosis = findViewById<View>(R.id.autoDosis) as AutoCompleteTextView
        autoPeso = findViewById<View>(R.id.autoPeso) as AutoCompleteTextView
        autoPaciente = findViewById<View>(R.id.autoPaciente) as AutoCompleteTextView
        edtEmail = findViewById<View>(R.id.edtEmail) as TextInputEditText
        btnContinuar = findViewById<View>(R.id.btnContinuar) as Button
        txtCancelar = findViewById<View>(R.id.txtCancelar) as TextView
        edtSelectedFecha = findViewById(R.id.edtSelectedFecha) as TextInputLayout
        lnlDetalleServicio = findViewById<View>(R.id.lnlDetalleServicio) as LinearLayout


        txtTitleVet!!.text = veterinaria.titulo
        val servicioArrayOf = arrayListOf<String>()
        servicioArrayOf.add("Vacunas")
        servicioArrayOf.add("Lavado")
        servicioArrayOf.add("Consulta Ambulatoria")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, servicioArrayOf
        )
        autoServicio?.setAdapter(adapter)
        autoServicio?.threshold = 1

        autoServicio?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }

        calendarViewVet!!.setMinDate(System.currentTimeMillis() - 1000);

        calendarViewVet!!.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            val msg = "Fecha " + dayOfMonth + "/" + (month + 1) + "/" + year
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            autoSelectedFecha!!.setText( dayOfMonth.toString() + "/" + (month + 1) + "/" + year)
            calendarViewVet!!.visibility = View.GONE
            edtSelectedFecha!!.visibility = View.VISIBLE
            lnlDetalleServicio!!.visibility = View.VISIBLE
        }

        val horariosArrayOf = arrayListOf<String>()
        for(hor in veterinaria.horarios.tiempo) {
            horariosArrayOf.add(hor)
        }

        val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, horariosArrayOf
        )
        autoHorarios?.setAdapter(adapter2)
        autoHorarios?.threshold = 1

        autoHorarios?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }

        val tipoArrayOf = arrayListOf<String>()
        tipoArrayOf.add("Vacunas")
        tipoArrayOf.add("Bañado")


        val adapter0: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, tipoArrayOf
        )
        autoTipo?.setAdapter(adapter0)
        autoTipo?.threshold = 1

        autoTipo?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }


        val dosisArrayOf = arrayListOf<String>()
        dosisArrayOf.add("Dosis anual")
        dosisArrayOf.add("Triple")
        dosisArrayOf.add("Contra la Rabia")


        val adapter3: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, dosisArrayOf
        )
        autoDosis?.setAdapter(adapter3)
        autoDosis?.threshold = 1

        autoDosis?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }

        val pesoArrayOf = arrayListOf<String>()
        pesoArrayOf.add("Entre 1 a 5kg")
        pesoArrayOf.add("Entre 5 a 10kg")
        pesoArrayOf.add("Entre 10 a 15kg")
        pesoArrayOf.add("Entre 15 a 20kg")

        val adapter4: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, pesoArrayOf
        )
        autoPeso?.setAdapter(adapter4)
        autoPeso?.threshold = 1

        autoPeso?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }

        val pacienteArrayOf = arrayListOf<String>()
        pacienteArrayOf.add("Gaia")
        pacienteArrayOf.add("Chuleta")
        pacienteArrayOf.add("Sandia")

        val adapter5: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line, pacienteArrayOf
        )
        autoPaciente?.setAdapter(adapter5)
        autoPaciente?.threshold = 1

        autoPaciente?.postDelayed(Runnable {
            autoPaciente?.setText(pacienteArrayOf[0], false);
        }, 10)

        autoPaciente?.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Utils.dump("categeoria selectedItem: $selectedItem")
        }

        btnContinuar!!.setOnClickListener {
            val intent = Intent(this, JourneyTipoOneThankYouActivity::class.java)
            startActivity(intent)
        }

        txtCancelar!!.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}