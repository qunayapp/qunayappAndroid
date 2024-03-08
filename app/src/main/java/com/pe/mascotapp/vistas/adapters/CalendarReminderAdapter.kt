package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemCalendarPetBinding
import com.pe.mascotapp.extentions.changeTintColor


class CalendarReminderAdapter(private val reminders: List<ReminderEntity>): RecyclerView.Adapter<CalendarReminderAdapter.CalendarReminderViewHolder>() {
    class  CalendarReminderViewHolder(private val binding: ItemCalendarPetBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(reminder: ReminderEntity) {
            handleState(reminder.isActivated)
        }
        private fun handleState(isActivated: Boolean) {
            var backgroundColor = R.color.verdeclaroq
            var primaryTextColor = R.color.white
            var secondaryTextColor = R.color.white
            var iconColor = R.color.white
            if (!isActivated){
                backgroundColor = R.color.green100
                primaryTextColor = R.color.plomoDark
                secondaryTextColor = R.color.plomoRegular
                iconColor = R.color.plomoRegular
            }
            binding.clParent.changeTintColor(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundColor
                )
            )
            binding.icDate.drawable.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    iconColor
                ),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.icLocation.drawable.setColorFilter(
                ContextCompat.getColor(
                    binding.root.context,
                    iconColor
                ),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.txtReminder.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    primaryTextColor
                )
            )
            binding.txtPetName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    primaryTextColor
                )
            )
            binding.txtDate.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    primaryTextColor
                )
            )
            binding.txtLocationName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    primaryTextColor
                )
            )
            binding.txtNotesTitle.setTextColor(
                ContextCompat.getColor(
                binding.root.context,
                primaryTextColor
            ))
            binding.txtPicturesTitle.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    primaryTextColor
                ))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarReminderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarPetBinding.inflate(layoutInflater, parent, false)
        return CalendarReminderViewHolder(binding)
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(holder: CalendarReminderViewHolder, position: Int) {
        holder.bind(reminders[position])
    }
}