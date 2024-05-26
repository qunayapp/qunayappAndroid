package com.pe.mascotapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pe.mascotapp.vistas.adapters.ReminderEntity
import com.pe.mascotapp.vistas.adapters.TypeOption
import com.pe.mascotapp.vistas.adapters.ValueTextOption
import com.pe.mascotapp.vistas.entities.CATEGORYID
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity

@Entity
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long? = null,
    val title: String,
    val description: String,
    val alarms: List<String>,
    val dateAlarms: List<String>,
    val isActivated: Boolean,
    val alarmInMinutes: Int = 15,
    val alarmInHours: Int = 0,
    val alarmInDays: Int = 0,
    val categoryReminder: CATEGORYID,
    val startDate: String,
    var endDate: String,
    var startHour: String,
    val listImages: List<String>,
    val repeatOption: ValueTextOption = ValueTextOption.DONT_REPEAT,
    val countRepeatOption: Int?,
    var times: Int,
    val durationTypeRepeat: TypeOption?,
    val durationRepeat: String?,
    val vaccines: List<String>,
) {
    fun alarmsPassed(): Boolean {
        return countRepeatOption == times
    }

    fun toReminderEntity(): ReminderEntity {
        return ReminderEntity(
            reminderId = this.reminderId ?: 0,
            title = this.title,
            description = this.description,
            startDate = this.startDate,
            endDate = this.endDate,
            startHour = this.startHour,
            location = "",
            categoryReminder = CategoryReminderEntity.getReminder(this.categoryReminder),
            isActivated = this.isActivated,
            alarmInMinutes = this.alarmInMinutes,
            alarmInHours = this.alarmInHours,
            alarmInDays = this.alarmInDays,
            listImages = this.listImages,
            repeatOption = this.repeatOption,
            this.countRepeatOption,
            this.times,
            this.durationTypeRepeat,
            this.durationRepeat,
            this.vaccines,
        )
    }
}

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}
