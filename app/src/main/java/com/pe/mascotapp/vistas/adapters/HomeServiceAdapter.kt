package com.pe.mascotapp.vistas.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.PromocionBanner

class HomeServiceAdapter(
    private val categorias: ArrayList<Categorias>,
    private val promocionBanner: PromocionBanner,
    private var listener: (Categorias) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categoriasList = ArrayList<Categorias>()
    var positionCategorySelected = 0

    init {
        categoriasList = categorias
    }

    class HomeServiceAdapterHolder(view: View, isActivated: Boolean) :
        RecyclerView.ViewHolder(view) {
        val imgHolder: ImageView = view.findViewById(R.id.imgHolder)
        private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val cardView: CardView = view.findViewById(R.id.cvItem)

        init {
            if (!isActivated) {
                cardView.setCardBackgroundColor(view.context.getColor(R.color.plomoq))
                imgHolder.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.plomoRegular
                    ),
                    PorterDuff.Mode.SRC_IN
                )
                txtTitle.setTextColor(view.context.getColor(R.color.plomoq))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (positionCategorySelected == position) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_home_holder, parent, false)
        val isActivated = viewType == 1
        return HomeServiceAdapterHolder(view, isActivated)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            positionCategorySelected = position
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return categoriasList.size
    }
}