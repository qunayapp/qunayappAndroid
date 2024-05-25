package com.pe.mascotapp.vistas.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pe.mascotapp.databinding.ItemImageBinding
import java.time.LocalDate

class ImageGalleryAdapter(
    var images: ArrayList<Uri>,
    val itemOnClick: (position: Int) -> Unit = { _ -> }
) :
    RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryAdapterViewHolder>() {

    class ImageGalleryAdapterViewHolder( val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Uri) {
            Glide.with(binding.root.context)
                .load(image)
                .into(binding.ivImage)
            binding.ivDelete.bringToFront()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
        return ImageGalleryAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImageGalleryAdapterViewHolder, position: Int) {
        holder.bind(images[position])
        holder.binding.ivDelete.setOnClickListener {
            val positionD= holder.adapterPosition
            images.removeAt(positionD)
            itemOnClick( positionD)
            notifyItemRemoved(positionD)
        }
    }

}