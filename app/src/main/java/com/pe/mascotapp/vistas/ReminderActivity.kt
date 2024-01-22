package com.pe.mascotapp.vistas

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pe.mascotapp.R
import com.pe.mascotapp.notifications.AlarmReceiver
import com.pe.mascotapp.notifications.NotificationService
import com.pe.mascotapp.utils.Utils
import java.util.Calendar


class ReminderActivity : AppCompatActivity() {

    var edtNombreEvento: TextInputLayout?= null
    var btnAceptar: MaterialButton?= null
    var btnSelectedDate: Button?= null
    var btnSetAlarm: Button?= null
    var btnCancelarAlarm: Button?= null
    var txtHora:TextView ?= null

    var autoServicio: AutoCompleteTextView?= null

    private lateinit var picker :MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager : AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        createNotificacionChannel()

        autoServicio = findViewById<AutoCompleteTextView>(R.id.autoServicio)

        edtNombreEvento = findViewById<TextInputLayout>(R.id.edtNombreEvento)
        btnAceptar = findViewById<MaterialButton>(R.id.btnAceptar)
        btnSelectedDate = findViewById<Button>(R.id.btnSelectedDate)
        btnSetAlarm = findViewById<Button>(R.id.btnSetAlarm)
        btnCancelarAlarm = findViewById<Button>(R.id.btnCancelarAlarm)
        txtHora = findViewById<Button>(R.id.txtHora)

        btnAceptar!!.setOnClickListener {

            startService()
            
        }

        btnSelectedDate!!.setOnClickListener {
            showTimePicker()
        }

        btnSetAlarm!!.setOnClickListener {
            setAlarm()
        }

        btnCancelarAlarm!!.setOnClickListener {
            cancelAlarm()
        }

        val servicioArrayOf = arrayListOf<String>()
        servicioArrayOf.add("Cuadruple")
        servicioArrayOf.add("Quintuple")
        servicioArrayOf.add("Sextuple")
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
    }



    private fun showTimePicker(){
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Alarma")
            .build()

        picker.show(supportFragmentManager,"foxandroid")

        picker.addOnPositiveButtonClickListener{
            if (picker.hour > 12){
                txtHora!!.text = String.format("%02d",picker.hour - 12) + " : " +
                        String.format("%02d", picker.minute) + "PM"
            }else{
                txtHora!!.text = String.format("%02d",picker.hour - 12) + " : " +
                        String.format("%02d", picker.minute) + "AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun setAlarm(){

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this,"Alarma creada",Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this,"Alarma cancelada",Toast.LENGTH_LONG).show()
    }

    private fun createNotificacionChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "foxandroidReminderChannel"
            val description = "Mensaje de alarma"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("foxandroid",name,importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun startService() {
        val input: String = "hola"
        val serviceIntent = Intent(this, NotificationService::class.java)
        serviceIntent.putExtra("inputExtra", input)
        ContextCompat.startForegroundService(this, serviceIntent)
    }
    fun stopService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        stopService(serviceIntent)
    }
}