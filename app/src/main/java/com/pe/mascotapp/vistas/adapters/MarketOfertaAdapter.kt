package com.pe.mascotapp.vistas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Categorias
import com.pe.mascotapp.modelos.Oferta

class MarketOfertaAdapter (private val oferta:ArrayList<Oferta>, private var listener:(Oferta)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ofertaList = ArrayList<Oferta>()

    init {
        ofertaList = oferta
    }

    class MarketOfertaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mContext: Context
        val imgHolder: ImageView

        init {
            imgHolder = view.findViewById(R.id.imgHolder)
            mContext = view.context
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
        //return if ((position + 1) % 2 == 0) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        /*return if (viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tipo_violencia_uno_holder,parent,false)
            SubModuloSegundoHolder(view)

        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tipo_violencia_dos_holder,parent,false)
            SubModuloSegundoHolderDerecha(view)
        }*/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.oferta_holder, parent, false)
        return MarketOfertaHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MarketOfertaHolder
        val name = ofertaList[position].img
        val drawable =
            holder.mContext.resources.getIdentifier(name, "drawable", holder.mContext.packageName)
        holder.imgHolder.setImageResource(drawable)

        holder.itemView.setOnClickListener { listener(ofertaList[position]) }

    }

    override fun getItemCount(): Int {
        return ofertaList.size
    }
}