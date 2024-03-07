package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemReminderBinding
import com.pe.mascotapp.extentions.changeTintColor
import com.pe.mascotapp.vistas.entities.CategoryReminderEntity
import com.pe.mascotapp.vistas.entities.PetEntity

class ReminderAdapter(private val reminders: List<ReminderEntity>) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reminder: ReminderEntity) {
            binding.reminder = reminder
            binding.ivReminder.setImageDrawable(
                reminder.categoryReminder?.image?.let {
                    ContextCompat.getDrawable(
                        binding.root.context,
                        it
                    )
                }

            )
            handleState(reminder.isActivated)
            binding.swReminder.setOnCheckedChangeListener { _, isChecked ->
                reminder.isActivated = isChecked
                handleState(reminder.isActivated)
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

class ReminderEntity(
    var title: String = "",
    val description: String = "",
    val listPets: List<PetEntity> = listOf(),
    var startDate: String = "",
    val endDate: String = "",
    var startHour: String = "",
    var endHour: String = "",
    val isAllDay: Boolean = false,
    val location: String = "",
    val categoryReminder: CategoryReminderEntity? = null,
    var isActivated: Boolean = true,
    var alarms: ArrayList<String> = arrayListOf(),
    var dateAlarms : ArrayList<String> = arrayListOf()
) {
    fun pets(): String {
        return this.listPets.joinToString(",")
    }
}
