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

class ComentariosAdapter (private val categorias:ArrayList<Categorias>, private var listener:(Categorias)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var categoriasList = ArrayList<Categorias>()

    init {
        categoriasList = categorias
    }

    class ComentariosAdapterHolder(view: View): RecyclerView.ViewHolder(view){
        /*val mContext: Context
        val imgHolder: ImageView
        val txtTitle: TextView*/

        init {
            /*imgHolder = view.findViewById(R.id.imgHolder)
            txtTitle = view.findViewById(R.id.txtTitle)
            mContext = view.context*/
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
            .inflate(R.layout.comentario_holder,parent,false)
        return ComentariosAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*holder as HomeListServiceAdapterHolder
        holder.txtTitle.text = categoriasList[position].titulo

        val name = categoriasList[position].img
        val drawable = holder.mContext.resources.getIdentifier(name,"drawable", holder.mContext.packageName)
        holder.imgHolder.setImageResource(drawable)*/

        holder.itemView.setOnClickListener { listener(categoriasList[position]) }

    }

    override fun getItemCount(): Int {
        return categoriasList.size
    }
}