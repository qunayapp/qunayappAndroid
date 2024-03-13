package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemReminderBinding
import com.pe.mascotapp.domain.models.Reminder
import com.pe.mascotapp.domain.models.ReminderWithPets
import com.pe.mascotapp.extentions.changeTintColor
import com.pe.mascotapp.vistas.entities.CATEGORYID
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity
import com.pe.mascotapp.vistas.entities.PetEntity

class ReminderAdapter(var reminders: List<ReminderPetsJoinEntity>) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reminderPets: ReminderPetsJoinEntity) {
            binding.reminderPets = reminderPets
            binding.ivReminder.setImageDrawable(
                reminderPets.reminder.categoryReminder?.image?.let {
                    ContextCompat.getDrawable(
                        binding.root.context,
                        it
                    )
                }

            )
            handleState(reminderPets.reminder.isActivated)
            binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
                reminderPets.reminder.isActivated = isChecked
                handleState(reminderPets.reminder.isActivated)
            }
        }

        private fun handleState(isActivated: Boolean) {
            var backgroundColor = R.color.green100
            var titleTextColor = R.color.black
            var itemTextColor = R.color.secondary
            var iconColor = R.color.white
            var backgroundIconColor = R.color.verdeclaroq
            if (!isActivated) {
                backgroundColor = R.color.plomoRegular
                titleTextColor = R.color.plomoDark
                itemTextColor = R.color.grey100
                iconColor = R.color.plomoRegular
                backgroundIconColor = R.color.plomoDark
            }
            binding.swReminder.isChecked = isActivated
            binding.clHeader.changeTintColor(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundColor
                )
            )
            binding.ivReminder.drawable.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    iconColor
                ),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.ivReminder.background.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundIconColor
                ),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.tvTitleReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    titleTextColor
                )
            )
            binding.tvAnimalName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor
                )
            )

            binding.tvDateReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor
                )
            )

            binding.tvLocationReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemReminderBinding.inflate(layoutInflater, parent, false)
        return ReminderViewHolder(binding)
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(reminders[position])
    }
}

class ReminderPetsJoinEntity(
    var reminder: ReminderEntity,
    var pets: List<PetEntity>
) {
    constructor(reminder: ReminderWithPets) : this(
        reminder.reminder.toReminderEntity(),
        reminder.pets.map { it.toPetEntity() }
    )

    fun pets(): String {
        return this.pets.joinToString(",") { it.name }
    }

    fun getNamesPets(): String{
        val arrayOfValues =  pets.map { it.name }.toTypedArray()
        return  arrayOfValues.joinToString(",")
    }

}

class ReminderEntity(
    var reminderId: Long? = null,
    var title: String = "",
    var description: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var startHour: String = "",
    var endHour: String = "",
    var isAllDay: Boolean = false,
    val location: String = "",
    var categoryReminder: CategoryReminderEntity? = null,
    var isActivated: Boolean = true,
    var alarms: ArrayList<String> = arrayListOf(),
    var dateAlarms: ArrayList<String> = arrayListOf(),
    var listImages: List<String> = listOf(),
    var repeatOption: ValueTextOption? = null,
    var durationTypeRepeat: TypeOption? = null,
    var durationRepeat: String? = null,
) {

    fun toReminder(): Reminder {
        return Reminder(
            title = this.title,
            description = this.description,
            startDate = this.startDate,
            endDate = this.endDate,
            isAllDay = this.isAllDay,
            categoryReminder = this.categoryReminder?.categoryId ?: CATEGORYID.OTHERS,
            isActivated = true,
            alarms = this.alarms,
            dateAlarms = this.dateAlarms,
            listImages = this.listImages,
            repeatOption = this.repeatOption,
            durationRepeat = this.durationRepeat,
            durationTypeRepeat = this.durationTypeRepeat,
            endHour = this.endHour,
            startHour = this.startHour
        )
    }

}

enum class TypeOption {
    COUNTER,
    TEXT,
    DATE
}