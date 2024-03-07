package com.pe.mascotapp.vistas

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_ALLOW_MULTIPLE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.databinding.ActivityReminderBinding
import com.pe.mascotapp.notifications.AlarmReceiver
import com.pe.mascotapp.notifications.AlarmReceiver.Companion.NOTIFICATION_ID
import com.pe.mascotapp.viewmodels.ReminderViewModel
import com.pe.mascotapp.vistas.adapters.CategoryReminderAdapter
import com.pe.mascotapp.vistas.adapters.ImageGalleryAdapter
import com.pe.mascotapp.vistas.adapters.OptionViewInterface
import com.pe.mascotapp.vistas.adapters.PetAdapter
import com.pe.mascotapp.vistas.adapters.VaccineFieldAdapter
import com.pe.mascotapp.vistas.dialogs.DialogOption
import com.pe.mascotapp.vistas.entities.VaccineFieldEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class ReminderActivity : AppCompatActivity() {

    companion object {
        const val MY_CHANNEL_ID = "myChannel"
        const val MY_CHANNEL_NAME = "MySuperChannel"
        const val TYPE_MEDIA = "image/*"
        const val GRID_CATEGORIES = 5
        const val GRID_IMAGES = 4
        const val REQUEST_CODE_PERMISSION = 10001
    }

    private lateinit var binding: ActivityReminderBinding

    private val viewModel: ReminderViewModel by viewModels()

    private val imageGalleryAdapter = ImageGalleryAdapter(listOf())

    private val pickImageFromGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) result.data?.let { addImages(it) }
    }

    private var permissionMediaLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        handlePermissionGallery(true in permissions.values)
    }

    private fun addImages(dataImages: Intent) {
        val images = mutableListOf<Uri>()
        val data: Intent = dataImages
        data.clipData?.itemCount?.let { itemCount ->
            for (i in 0 until itemCount) {
                data.clipData?.getItemAt(i)?.let { images.add(it.uri) }
            }
        }
        imageGalleryAdapter.images = images
        imageGalleryAdapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
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
            layoutManager = GridLayoutManager(context, GRID_CATEGORIES)
        }
        binding.rvImages.apply {
            layoutManager = GridLayoutManager(context, GRID_IMAGES)
            adapter = imageGalleryAdapter
        }
        val vaccineAdapter = VaccineFieldAdapter(viewModel.listVaccines)
        vaccineAdapter.addVaccineField = {
            viewModel.listVaccines.add(VaccineFieldEntity())
            vaccineAdapter.notifyItemInserted(viewModel.listVaccines.size)
        }
        vaccineAdapter.removeVaccine = {
            viewModel.listVaccines.removeAt(it)
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
        binding.reminderViewModel = viewModel

        viewModel.getSelectCategories()
        viewModel.getPets()

        checkPermission()
        createChannel()
        scheduleNotification()

        setUpObservables()
        setUpRecyclerViews()
        setUpListeners()

        setContentView(binding.root)
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + 15000, pendingIntent)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                MY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_PERMISSION)
        }
    }

    private fun setUpListeners() {
        binding.nameReminder.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setNameReminder(s.toString())
            }
        })
        binding.swReminder.setOnCheckedChangeListener { _, isEnable ->
            binding.tvHourEnd.isEnabled = !isEnable
            binding.tvHourStart.isEnabled = !isEnable
            viewModel.setAllDay(isEnable)
        }
        binding.llAddImage.setOnClickListener {
            verifyPermissionGallery()
        }
        binding.llAlarm.setOnClickListener {
            viewModel.getAlarmOptions()
        }
        binding.tvRepeat.setOnClickListener {
            viewModel.getOptionsRepeat()
        }
        binding.tvAddDuration.setOnClickListener {
            viewModel.getOptionsDurationRepeat()
        }
        binding.tvDateEnd.setOnClickListener {
            viewModel.getOptionEndDate()
        }
        binding.tvDateStart.setOnClickListener {
            viewModel.getOptionStartDate()
        }
        binding.tvHourEnd.setOnClickListener {
            viewModel.getOptionEndHour()
        }
        binding.tvHourStart.setOnClickListener {
            viewModel.getOptionStartHour()
        }
        binding.buttonAccept.setOnClickListener {
            viewModel.createReminder()
        }
    }

    private fun handlePermissionGallery(isGranted: Boolean) {
        if (isGranted) {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = TYPE_MEDIA
                putExtra(EXTRA_ALLOW_MULTIPLE, true)
            }
            pickImageFromGalleryLauncher.launch(intent)
        }
    }

    private fun setUpObservables() {
        viewModel.listPets.observe(this) {
            binding.rvAnimals.adapter = PetAdapter(it) { viewModel.enableForm() }
        }
        viewModel.categoriesReminder.observe(this) {
            binding.rvCategories.adapter = CategoryReminderAdapter(it) { viewModel.enableForm() }
        }
        viewModel.listOptionsRepeat.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getOptionRepeat()?.let { binding.tvRepeat.text = it }
            }
        }
        viewModel.listDurationRepeat.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getDurationRepeat()?.let { binding.tvAddDuration.text = it }
            }
        }
        viewModel.listAlarms.observe(this) {
            val dialogOptions = DialogOption()
            dialogOptions.setListListOptions(it)
            dialogOptions.setCanAddOtherContainer(true)
            dialogOptions.setCallBackOptions {
                binding.tvAlarm.text = viewModel.getAlarms()
            }
            dialogOptions.show(supportFragmentManager, DialogOption::class.java.simpleName)
        }
        viewModel.optionEndHour.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getEndHourSelected()?.let { binding.tvHourEnd.text = it }
            }
        }
        viewModel.optionStartHour.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getStartHourSelected()?.let { binding.tvHourStart.text = it }
            }
        }
        viewModel.optionStartDate.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getStartDateSelected()?.let { binding.tvDateStart.text = it }
            }
        }
        viewModel.optionEndDate.observe(this) { options ->
            showDialogOptions(options) {
                viewModel.getEndDateSelected()?.let { binding.tvDateEnd.text = it }
            }
        }
    }

    private fun showDialogOptions(listOptions: List<OptionViewInterface>, callBackOptions: () -> Unit) {
        val dialogOptions = DialogOption()
        dialogOptions.setListOptions(listOptions)
        dialogOptions.setCanAddOtherContainer(false)
        dialogOptions.setCallBackOptions { callBackOptions() }
        dialogOptions.show(supportFragmentManager, DialogOption::class.java.simpleName)
    }

}