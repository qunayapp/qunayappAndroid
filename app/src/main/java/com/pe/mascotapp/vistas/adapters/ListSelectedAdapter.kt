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
import com.pe.mascotapp.modelos.Raza

class ListSelectedAdapter (private val razas:ArrayList<Raza>, private var listener:(Raza,ListSelectedAdapterHolder)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var razaList = ArrayList<Raza>()

    init {
        razaList = razas
    }

    class ListSelectedAdapterHolder(view: View): RecyclerView.ViewHolder(view){
        val mContext: Context
        val txtDescripcion: TextView
        val imgCheck:ImageView

        init {
            txtDescripcion = view.findViewById(R.id.txtDescripcion)
            imgCheck = view.findViewById(R.id.imgCheck)
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
            .inflate(R.layout.raza_holder,parent,false)
        return ListSelectedAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ListSelectedAdapterHolder
        holder.txtDescripcion.text = razaList[position].nombre

        holder.itemView.setOnClickListener {
            listener(razaList[position],holder)
        }

    }

    override fun getItemCount(): Int {
        return razaList.size
    }
}