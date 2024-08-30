package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.os.Parcelable
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
import kotlinx.parcelize.Parcelize

class ReminderAdapter(
    var reminders: MutableList<ReminderPetsJoinEntity>,
    var totalReminders : MutableList<ReminderPetsJoinEntity>,
    val updateReminder: (ReminderEntity) -> Unit,
    val navigateEditReminder: (ReminderPetsJoinEntity) -> Unit,
) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            reminderPets: ReminderPetsJoinEntity,
            updateReminder: (ReminderEntity) -> Unit,
            navigateEditReminder: (ReminderPetsJoinEntity) -> Unit,
        ) {
            binding.reminderPets = reminderPets
            binding.ivReminder.setImageDrawable(
                reminderPets.reminder.categoryReminder?.image?.let {
                    ContextCompat.getDrawable(
                        binding.root.context,
                        it,
                    )
                },
            )
            binding.ivEditReminder.setOnClickListener {
                navigateEditReminder(reminderPets)
            }
            handleState(reminderPets.reminder.isActivated)
            binding.swReminder.setOnCheckedChangeListener(null)
            binding.swReminder.isChecked = reminderPets.reminder.isActivated
            binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked == reminderPets.reminder.isActivated) return@setOnCheckedChangeListener
                reminderPets.reminder.isActivated = isChecked
                updateReminder(reminderPets.reminder)
                handleState(reminderPets.reminder.isActivated)
            }
        }

        private fun handleState(isActivated: Boolean) {
            var backgroundColor = R.color.backgroundCard
            var titleTextColor = R.color.black
            var itemTextColor = R.color.secondary
            var iconColor = R.color.white
            var backgroundIconColor = R.color.blue_primary
            if (!isActivated) {
                backgroundColor = R.color.plomoRegular
                titleTextColor = R.color.plomoDark
                itemTextColor = R.color.grey100
                iconColor = R.color.plomoRegular
                backgroundIconColor = R.color.plomoDark
            }
            binding.clHeader.changeTintColor(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundColor,
                ),
            )
            binding.ivReminder.drawable.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    iconColor,
                ),
                PorterDuff.Mode.SRC_ATOP,
            )
            binding.ivReminder.background.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundIconColor,
                ),
                PorterDuff.Mode.SRC_ATOP,
            )
            binding.tvTitleReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    titleTextColor,
                ),
            )
            binding.tvAnimalName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor,
                ),
            )

            binding.tvDateReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor,
                ),
            )

            binding.tvLocationReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    itemTextColor,
                ),
            )
        }
    }
    fun addItems(newItems: List<ReminderPetsJoinEntity>) {
        reminders.addAll(newItems)
        totalReminders.addAll(newItems)
        notifyItemRangeInserted(reminders.size - newItems.size, newItems.size)
    }

    fun clearList() {
        reminders.clear()
        totalReminders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemReminderBinding.inflate(layoutInflater, parent, false)
        return ReminderViewHolder(binding)
    }

    fun filterPets(id: Long?) {
        val filterList =
            if (id != null) {
                totalReminders.filter { it.pets.firstOrNull { it.petId == id } != null }
            } else {
                totalReminders
            }
        reminders.clear()
        reminders.addAll(filterList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(
        holder: ReminderViewHolder,
        position: Int,
    ) {
        holder.bind(reminders[position], updateReminder, navigateEditReminder)
    }
}

@Parcelize
class ReminderPetsJoinEntity(
    var reminder: ReminderEntity,
    var pets: List<PetEntity>,
) : Parcelable {
    constructor(reminder: ReminderWithPets) : this(
        reminder.reminder.toReminderEntity(),
        reminder.pets.map { it.toPetEntity() },
    )

    fun pets(): String {
        return this.pets.joinToString(",") { it.name }
    }

    fun getNamesPets(): String{
        val arrayOfValues =  pets.map { it.name }.toTypedArray()
        return  arrayOfValues.joinToString(",")
    }

}

@Parcelize
class ReminderEntity(
    var reminderId: Long? = null,
    var title: String = "",
    var description: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var startHour: String = "12:00",
    val location: String = "",
    var categoryReminder: CategoryReminderEntity? = null,
    var isActivated: Boolean = true,
    var alarmInMinutes: Int = 15,
    var alarmInHours: Int = 0,
    var alarmInDays:Int = 0,
    var listImages: List<String> = listOf(),
    var repeatOption: ValueTextOption = ValueTextOption.DONT_REPEAT,
    var countRepeatOption: Int? = null,
    var times: Int = 0,
    var durationTypeRepeat: TypeOption? = null,
    var durationRepeat: String? = null,
    var vaccines: List<String> = listOf(),
) : Parcelable {
    fun toReminder(): Reminder {
        return Reminder(
            reminderId = reminderId,
            title = this.title,
            description = this.description,
            startDate = this.startDate,
            endDate = this.endDate,
            categoryReminder = this.categoryReminder?.categoryId ?: CATEGORYID.OTHERS,
            isActivated = this.isActivated,
            alarms = arrayListOf(),
            alarmInMinutes = this.alarmInMinutes,
            alarmInHours = this.alarmInHours,
            alarmInDays = this.alarmInDays,
            dateAlarms = arrayListOf(),
            listImages = this.listImages,
            repeatOption = this.repeatOption,
            durationRepeat = this.durationRepeat,
            durationTypeRepeat = this.durationTypeRepeat,
            startHour = this.startHour,
            times = this.times,
            countRepeatOption = this.countRepeatOption,
            vaccines = this.vaccines,
        )
    }

}

enum class TypeOption {
    COUNTER,
    TEXT,
    DATE,
}
