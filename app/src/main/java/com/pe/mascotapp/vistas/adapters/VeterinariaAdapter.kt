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
import com.pe.mascotapp.modelos.PromocionBanner
import com.pe.mascotapp.modelos.Veterinaria

class VeterinariaAdapter (private val veterinaria:ArrayList<Veterinaria>,private var listener:(Veterinaria)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var veterinariaList = ArrayList<Veterinaria>()

    init {
        veterinariaList = veterinaria
    }

    class VeterinariaAdapterHolder(view: View): RecyclerView.ViewHolder(view){
        val mContext: Context
        val imgHolder: ImageView
        val txtNameVet: TextView
        val imgStar: ImageView

        init {
            imgHolder = view.findViewById(R.id.imgHolder)
            txtNameVet = view.findViewById(R.id.txtNameVet)
            imgStar = view.findViewById(R.id.imgStar)
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
            .inflate(R.layout.veterinaria_holder,parent,false)
        return VeterinariaAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as VeterinariaAdapterHolder
        holder.txtNameVet.text = veterinariaList[position].titulo
        if(veterinariaList[position].favorito){
            holder.imgStar.setImageResource(R.drawable.ic_baseline_star_24)
        }
        val name = veterinariaList[position].img
        val drawable = holder.mContext.resources.getIdentifier(name,"drawable", holder.mContext.packageName)
        holder.imgHolder.setImageResource(drawable)

        holder.itemView.setOnClickListener { listener(veterinariaList[position]) }

    }

    override fun getItemCount(): Int {
        return veterinariaList.size
    }
}