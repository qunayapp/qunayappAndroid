package com.pe.mascotapp.vistas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Market
import com.pe.mascotapp.modelos.Oferta

class MarketAdapter(private val oferta:ArrayList<Market>, private var listener:(Market)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var marketList = ArrayList<Market>()

    init {
        marketList = oferta
    }

    class MarketAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mContext: Context
        val imgHolder: ImageView
        val txtTitle:TextView

        init {
            imgHolder = view.findViewById(R.id.imgHolder)
            txtTitle = view.findViewById(R.id.txtTitle)
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
            .inflate(R.layout.categoria_home_holder, parent, false)
        return MarketAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MarketAdapterHolder
        val name = marketList[position].img
        holder.txtTitle.text = marketList[position].titulo
        val drawable =
            holder.mContext.resources.getIdentifier(name, "drawable", holder.mContext.packageName)
        holder.imgHolder.setImageResource(drawable)

        holder.itemView.setOnClickListener { listener(marketList[position]) }

    }

    override fun getItemCount(): Int {
        return marketList.size
    }
}