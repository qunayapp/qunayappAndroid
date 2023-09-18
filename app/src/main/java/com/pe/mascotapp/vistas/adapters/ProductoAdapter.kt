package com.pe.mascotapp.vistas.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pe.mascotapp.R
import com.pe.mascotapp.modelos.Oferta
import com.pe.mascotapp.modelos.Producto
import android.text.style.UnderlineSpan

import android.text.SpannableString




class ProductoAdapter (private val producto:ArrayList<Producto>, private var listener:(Producto)->Unit ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var productoList = ArrayList<Producto>()

    init {
        productoList = producto
    }

    class ProductoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mContext: Context
        val imgHolder: ImageView
        val txtMarca:TextView
        val txtProducto:TextView
        val txtCantidadUnidad:TextView
        val txtUnidad:TextView
        val txtPrecio:TextView
        val txtDescuento:TextView


        init {
            imgHolder = view.findViewById(R.id.imgHolder)
            txtMarca = view.findViewById(R.id.txtMarca)
            txtProducto = view.findViewById(R.id.txtProducto)
            txtCantidadUnidad = view.findViewById(R.id.txtCantidadUnidad)
            txtUnidad = view.findViewById(R.id.txtUnidad)
            txtPrecio = view.findViewById(R.id.txtPrecio)
            txtDescuento = view.findViewById(R.id.txtDescuento)
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
            .inflate(R.layout.producto_holder, parent, false)
        return ProductoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ProductoHolder
        val name = productoList[position].img
        holder.txtMarca.text = producto[position].marca
        holder.txtProducto.text = producto[position].titulo
        holder.txtCantidadUnidad.text = producto[position].cantidadUnidad
        holder.txtUnidad.text = producto[position].unidad
        holder.txtPrecio.text = producto[position].precio

        if(producto[position].oferta.length > 0){
            holder.txtPrecio.text = producto[position].precio
            holder.txtPrecio.setPaintFlags(holder.txtPrecio.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            holder.txtDescuento.text = producto[position].oferta
            holder.txtDescuento.visibility = View.VISIBLE
        }

        val drawable =
            holder.mContext.resources.getIdentifier(name, "drawable", holder.mContext.packageName)
        holder.imgHolder.setImageResource(drawable)

        holder.itemView.setOnClickListener { listener(productoList[position]) }

    }

    override fun getItemCount(): Int {
        return productoList.size
    }
}