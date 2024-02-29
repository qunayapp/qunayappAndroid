package com.pe.mascotapp.vistas

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Intent.EXTRA_ALLOW_MULTIPLE
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.pe.mascotapp.viewmodels.ReminderViewModel
import com.pe.mascotapp.vistas.adapters.CategoryReminderAdapter
import com.pe.mascotapp.vistas.adapters.ImageGalleryAdapter
import com.pe.mascotapp.vistas.adapters.PetAdapter
import com.pe.mascotapp.vistas.adapters.VaccineFieldAdapter
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity
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
    private val imageGalleryAdapter = ImageGalleryAdapter(listOf())
    private val pickImageFromGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val images = mutableListOf<Uri>()
                Log.e("quack", "Asdfadf1231231321312")
                try {
                    val data: Intent? = result.data

                    data?.clipData?.itemCount?.let {
                        Log.e("quack", it.toString())
                        for (i in 0 until it) {
                            data.clipData?.getItemAt(i)?.let {
                                images.add(it.uri)
                            }
                        }
                        Log.e("quack", images.toString())
                    }
                    imageGalleryAdapter.images = images
                    imageGalleryAdapter.notifyDataSetChanged()
                } catch (e: java.lang.Exception) {
                    Log.e("quack", e.localizedMessage)
                }
            }
        }
    }

    private var permissionMediaLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        handlePermissionGallery(true in permissions.values)
    }

    private val settingsGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        verifyPermissionGallery()
    }

    private fun verifyPermissionGallery() {
        val permissions = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionMediaLauncher.launch(permissions)
    }

    private fun setUpRecyclerViews() {
        binding.rvAnimals.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 5)
        }
        binding.rvImages.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = imageGalleryAdapter
        }
        val vaccineAdapter = VaccineFieldAdapter(reminderViewModel.listVaccines)
        vaccineAdapter.addVaccineField = {
            reminderViewModel.listVaccines.add(VaccineFieldEntity())
            vaccineAdapter.notifyItemInserted(reminderViewModel.listVaccines.size)
        }
        vaccineAdapter.removeVaccine = {
            reminderViewModel.listVaccines.removeAt(it)
            vaccineAdapter.notifyItemRemoved(it)
        }
        binding.rvVaccineFields.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vaccineAdapter
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
        setUpListeners()
        setContentView(binding.root)
        createNotificacionChannel()
    }

    private fun setUpListeners() {
        binding.llAddImage.setOnClickListener {
            verifyPermissionGallery()
        }
    }

    private fun handlePermissionGallery(isGranted: Boolean) {
        if (isGranted) {
            try {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(EXTRA_ALLOW_MULTIPLE, true)
                }
                pickImageFromGalleryLauncher.launch(intent)
            } catch (e: Exception) {
                Log.e("quack", e.localizedMessage)
            }
            return
        }
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", this@ReminderActivity.packageName, null)
        }
        settingsGalleryLauncher.launch(intent)
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
        picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setHour(12).setMinute(0).setTitleText("Alarma").build()

        picker.show(supportFragmentManager, "foxandroid")

        picker.addOnPositiveButtonClickListener {

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

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )

        Toast.makeText(this, "Alarma creada", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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