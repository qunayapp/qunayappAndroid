package com.pe.mascotapp.vistas

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pe.mascotapp.databinding.ActivityReminderBinding
import com.pe.mascotapp.notifications.AlarmReceiver
import com.pe.mascotapp.notifications.NotificationService
import com.pe.mascotapp.utils.Utils
import com.pe.mascotapp.viewmodels.ReminderViewModel
import com.pe.mascotapp.vistas.adapters.CategoryReminderAdapter
import com.pe.mascotapp.vistas.adapters.PetAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    private val reminderViewModel: ReminderViewModel by viewModels()

    private fun setUpRecyclerViews() {
        binding.rvAnimals.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        binding.reminderViewModel = reminderViewModel
        reminderViewModel.getSelectCategories()
        reminderViewModel.getPets()
        setUpObservables()
        setUpRecyclerViews()
        setContentView(binding.root)
        createNotificacionChannel()

        binding.btnAceptar!!.setOnClickListener {

            startService()

        }

        binding.btnSelectedDate!!.setOnClickListener {
            showTimePicker()
        }

        binding.btnSetAlarm!!.setOnClickListener {
            setAlarm()
        }

        binding.btnCancelarAlarm!!.setOnClickListener {
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

        binding.autoServicio?.setAdapter(adapter)
        binding.autoServicio?.threshold = 1

        binding.autoServicio?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                Utils.dump("categeoria selectedItem: $selectedItem")
            }
    }

    private fun setUpObservables() {
        reminderViewModel.listPets.observe(this) {
            binding.rvAnimals.adapter = PetAdapter(it) {
                reminderViewModel.enableForm()
            }
        }
        reminderViewModel.categoriesReminder.observe(this) {
            binding.rvCategories.adapter = CategoryReminderAdapter(it) {
                reminderViewModel.enableForm()
            }
        }
    }


    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Alarma")
            .build()

        picker.show(supportFragmentManager, "foxandroid")

        picker.addOnPositiveButtonClickListener {
            if (picker.hour > 12) {
                binding.txtHora!!.text = String.format("%02d", picker.hour - 12) + " : " +
                        String.format("%02d", picker.minute) + "PM"
            } else {
                binding.txtHora!!.text = String.format("%02d", picker.hour - 12) + " : " +
                        String.format("%02d", picker.minute) + "AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun setAlarm() {

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this, "Alarma creada", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_LONG).show()
    }

    private fun createNotificacionChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "foxandroidReminderChannel"
            val description = "Mensaje de alarma"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("foxandroid", name, importance)
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
}