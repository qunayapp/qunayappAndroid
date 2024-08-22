package com.pe.mascotapp.vistas.adapters

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pe.mascotapp.R
import com.pe.mascotapp.databinding.ItemCalendarPetBinding
import com.pe.mascotapp.extentions.changeTintColor


class CalendarReminderAdapter(val reminders: List<ReminderPetsJoinEntity>): RecyclerView.Adapter<CalendarReminderAdapter.CalendarReminderViewHolder>() {
    class  CalendarReminderViewHolder(private val binding: ItemCalendarPetBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(reminder: ReminderPetsJoinEntity) {
            handleState(reminder.reminder.isActivated)
            Glide.with(binding.root.context)
                .load(reminder.pets.get(0).image)
                .placeholder(R.drawable.perro1)
                .error(R.drawable.perro1)
                .into(binding.petImage)
            binding.reminder = reminder.reminder
            binding.txtPetName.text = reminder.getNamesPets()
            itemView.setOnClickListener {
                binding.grpSecondQuestion.visibility = if(binding.grpSecondQuestion.isVisible) View.GONE else View.VISIBLE
            }
        }
        private fun handleState(isActivated: Boolean) {
            var backgroundColor = R.color.blue_primary
            var primaryTextColor = R.color.white
            var iconColor = R.color.white
            if (!isActivated){
                backgroundColor = R.color.green100
                primaryTextColor = R.color.plomoDark
                iconColor = R.color.plomoRegular
            }
            binding.clParent.changeTintColor(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundColor
                )
            )
            ImageViewCompat.setImageTintList( binding.icDate, ColorStateList.valueOf(ContextCompat.getColor( binding.root.context, iconColor)))
            ImageViewCompat.setImageTintList( binding.icLocation, ColorStateList.valueOf(ContextCompat.getColor( binding.root.context, iconColor)))
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