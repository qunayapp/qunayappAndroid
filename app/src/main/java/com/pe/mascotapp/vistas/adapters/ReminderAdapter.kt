package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemReminderBinding
import com.pe.mascotapp.extentions.changeTintColor

class ReminderAdapter(private val reminders: List<ReminderEntity>) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    class ReminderViewHolder(private val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reminder: ReminderEntity) {
            binding.reminder = reminder
            binding.ivReminder.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    reminder.imageReminder
                )
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
    val title: String,
    val description: String,
    val animalName: String,
    val date: String,
    val location: String,
    val imageReminder: Int,
    var isActivated: Boolean = true
)
