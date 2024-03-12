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
    val categoryReminder: CATEGORYID,
    val startDate: String,
    var endDate: String,
    var startHour: String,
    var endHour: String,
    val isAllDay: Boolean,
    val listImages: List<String>,
    val repeatOption: ValueTextOption?,
    val durationTypeRepeat: TypeOption?,
    val durationRepeat: String?
) {
    fun toReminderEntity(): ReminderEntity {
        return ReminderEntity(
            this.reminderId ?: 0,
            this.title,
            this.description,
            this.startDate,
            this.endDate,
            this.startHour,
            this.endHour,
            this.isAllDay,
            "",
            CategoryReminderEntity.getReminder(this.categoryReminder),
            this.isActivated,
            ArrayList(this.alarms),
            ArrayList(this.dateAlarms),
            this.listImages,
            this.repeatOption,
            this.durationTypeRepeat,
            this.durationRepeat
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